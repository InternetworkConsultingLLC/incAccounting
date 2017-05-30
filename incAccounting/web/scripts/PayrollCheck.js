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
        var preroundTotal = nQty * nRate;
	nTotal = round(preroundTotal, nSettingDocumentMoneyDecimals);
	
	document.getElementById(sPrefix + "Quantity" + sGuid).value = round(nQty, nSettingDocumentQuantityDecimals);
	document.getElementById(sPrefix + "Rate" + sGuid).value = round(nRate, nSettingDocumentQuantityDecimals);
	document.getElementById(sPrefix + "Total" + sGuid).value = round(nTotal, nSettingDocumentQuantityDecimals);
	
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

	nQty = round(nQty, nSettingDocumentQuantityDecimals);
	nPrice = round(nRate, nSettingDocumentMoneyDecimals);
	var preroundedTotal = ((nQty * 10) * (nRate * 10)) / 100;
	nTotal = round(preroundedTotal, nSettingDocumentMoneyDecimals);
        
	document.getElementById(sPrefix + "Quantity" + sGuid).value = round(nQty, nSettingDocumentQuantityDecimals);
	document.getElementById(sPrefix + "Rate" + sGuid).value = round(nRate, nSettingDocumentQuantityDecimals);
	document.getElementById(sPrefix + "Total" + sGuid).value = round(nTotal, nSettingDocumentQuantityDecimals);
		
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
	
	document.getElementById("Gross").value = round(nGross, nSettingDocumentMoneyDecimals);
	document.getElementById("Adjusted").value = round(nAdjGross, nSettingDocumentMoneyDecimals);
	document.getElementById("Withheld").value = round(nWithheld, nSettingDocumentMoneyDecimals);
	document.getElementById("Company").value = round(nCompany, nSettingDocumentMoneyDecimals);
	
	document.getElementById("span-Adjusted-Gross").textContent = round(nAdjGross, nSettingDocumentMoneyDecimals);
	document.getElementById("span-Employee-Paid").textContent = round(nWithheld, nSettingDocumentMoneyDecimals);
	document.getElementById("span-Paycheck-Amount").textContent = round(nTakeHome, nSettingDocumentMoneyDecimals);
	
	document.getElementById("span-Compensation").textContent = round(nGross, nSettingDocumentMoneyDecimals);
	document.getElementById("span-Company-Paid").textContent = round(nCompany, nSettingDocumentMoneyDecimals);		
	document.getElementById("span-Total-Costs").textContent = round(nTotalExpenses, nSettingDocumentMoneyDecimals);
	
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