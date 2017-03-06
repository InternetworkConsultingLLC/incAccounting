function ignoreerror() { return true; }
window.onerror=ignoreerror();

var bSkip = false;

function changedQuantity() { 
	var id = this.id;
	if(!id)
		return;
	calculateQuantity(id); 
}
function calculateQuantity(id) {
	var sTarget = "Quantity";
	var nIndex = id.indexOf("Quantity");
	if(nIndex < 0)
		return;
	
	var sName = id.substr(0, nIndex + sTarget.length);
	if(!id.startsWith(sName))
		return;
	
	var sPrefix = sName.replace(sTarget, "");
	var sGuid = id.replace(sName, "");	
	
	var nQty = document.getElementById(sPrefix + "Quantity" + sGuid).value;
	var nRate = document.getElementById(sPrefix + "Rate" + sGuid).value;

	if(!isFinite(nQty)) {
		window.alert("Quantity is invalid!");
		return;
	}
	
	if(!isFinite(nPrice))
		return;	

	nTotal = new Number(nQty * nRate).toFixed(nSettingDocumentMoneyDecimals);
	
	document.getElementById(sPrefix + "Quantity" + sGuid).value = new Number(nQty).toFixed(nSettingDocumentQuantityDecimals);
	document.getElementById(sPrefix + "Rate" + sGuid).value = new Number(nRate).toFixed(nSettingDocumentQuantityDecimals);
	document.getElementById(sPrefix + "Total" + sGuid).value = new Number(nTotal).toFixed(nSettingDocumentQuantityDecimals);
	
	documentChanged();
}

function changedRate() { 
	var id = this.id;
	if(!id)
		return;
	calculateRate(id); 
}
function calculateRate(id) {
	var sTarget = "Rate";
	var nIndex = id.indexOf("Rate");
	if(nIndex < 0)
		return;
	
	var sName = id.substr(0, nIndex + sTarget.length);
	if(!id.startsWith(sName))
		return;
	
	var sPrefix = sName.replace(sTarget, "");
	var sGuid = id.replace(sName, "");	
	
	var nQty = document.getElementById(sPrefix + "Quantity" + sGuid).value;
	var nRate = document.getElementById(sPrefix + "Rate" + sGuid).value;

	if(!isFinite(nRate)) {
		window.alert("Rate is invalid!");
		return;
	}
	
	if(!isFinite(nQty))
		return;	

	nQty = new Number(nQty).toFixed(nSettingDocumentQuantityDecimals);
	nPrice = new Number(nRate).toFixed(nSettingDocumentMoneyDecimals);
	nTotal = new Number(nQty * nRate).toFixed(nSettingDocumentMoneyDecimals);
	
	document.getElementById(sPrefix + "Quantity" + sGuid).value = new Number(nQty).toFixed(nSettingDocumentQuantityDecimals);
	document.getElementById(sPrefix + "Rate" + sGuid).value = new Number(nRate).toFixed(nSettingDocumentQuantityDecimals);
	document.getElementById(sPrefix + "Total" + sGuid).value = new Number(nTotal).toFixed(nSettingDocumentQuantityDecimals);
	
	documentChanged();
}

function documentChanged() {
	if(bSkip)
		return;
	bSkip = true;
	
	var nGross = new Number(0);
	var nAdjGross = new Number(0);
	var nCompany = new Number(0);
	var nWithheld = new Number(0);	
	
	arrControls = document.getElementsByClassName("GrossTotal");
	for (var cnt = 0; cnt < arrControls.length; cnt++) {
		calculateRate(document.getElementById(arrControls[cnt].id.replace("Total", "Rate")).id);
		var nAmount = new Number(arrControls[cnt].value);
		if(nAmount > 0)
			nGross = nGross + nAmount;
		nAdjGross = nAdjGross + nAmount;
	}

	arrControls = document.getElementsByClassName("CompanyTotal");
	for (var cnt = 0; cnt < arrControls.length; cnt++) {
		calculateRate(document.getElementById(arrControls[cnt].id.replace("Total", "Rate")).id);
		var nAmount = new Number(arrControls[cnt].value);
		nCompany = nCompany + nAmount;
	}
	
	arrControls = document.getElementsByClassName("EmployeeTotal");
	for (var cnt = 0; cnt < arrControls.length; cnt++) {
		calculateRate(document.getElementById(arrControls[cnt].id.replace("Total", "Rate")).id);
		var nAmount = new Number(arrControls[cnt].value);
		nWithheld = nWithheld + nAmount;
	}

	var nTakeHome = nAdjGross - nWithheld;
	var nTotalExpenses = nGross + nCompany;
	
	document.getElementById("Gross").value = new Number(nGross).toFixed(nSettingDocumentMoneyDecimals);
	document.getElementById("Adjusted").value = new Number(nAdjGross).toFixed(nSettingDocumentMoneyDecimals);
	document.getElementById("Withheld").value = new Number(nWithheld).toFixed(nSettingDocumentMoneyDecimals);
	document.getElementById("Company").value = new Number(nCompany).toFixed(nSettingDocumentMoneyDecimals);
	
	document.getElementById("span-Adjusted-Gross").textContent = new Number(nAdjGross).toFixed(nSettingDocumentMoneyDecimals);
	document.getElementById("span-Employee-Paid").textContent = new Number(nWithheld).toFixed(nSettingDocumentMoneyDecimals);
	document.getElementById("span-Paycheck-Amount").textContent = new Number(nTakeHome).toFixed(nSettingDocumentMoneyDecimals);
	
	document.getElementById("span-Compensation").textContent = new Number(nGross).toFixed(nSettingDocumentMoneyDecimals);
	document.getElementById("span-Company-Paid").textContent = new Number(nCompany).toFixed(nSettingDocumentMoneyDecimals);		
	document.getElementById("span-Total-Costs").textContent = new Number(nTotalExpenses).toFixed(nSettingDocumentMoneyDecimals);
	
	bSkip = false;
}

function pageLoaded() {
	var arrControls = document.getElementsByClassName("GrossQuantity");
	for (var cnt = 0; cnt < arrControls.length; cnt++)
		arrControls[cnt].onchange = changedQuantity;
	
	var arrControls = document.getElementsByClassName("EmployeeQuantity");
	for (var cnt = 0; cnt < arrControls.length; cnt++)
		arrControls[cnt].onchange = changedQuantity;
	
	var arrControls = document.getElementsByClassName("CompanyQuantity");
	for (var cnt = 0; cnt < arrControls.length; cnt++)
		arrControls[cnt].onchange = changedQuantity;

	arrControls = document.getElementsByClassName("GrossRate");
	for (var cnt = 0; cnt < arrControls.length; cnt++)
		arrControls[cnt].onchange = changedRate;

	arrControls = document.getElementsByClassName("EmployeeRate");
	for (var cnt = 0; cnt < arrControls.length; cnt++)
		arrControls[cnt].onchange = changedRate;

	arrControls = document.getElementsByClassName("CompanyRate");
	for (var cnt = 0; cnt < arrControls.length; cnt++)
		arrControls[cnt].onchange = changedRate;
}