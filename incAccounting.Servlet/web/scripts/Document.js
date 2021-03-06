function ignoreerror() { return true; }
window.onerror=ignoreerror();


function changedDate() {
	var sDate = document.getElementById("Date").value;
	
	var sYear = Number(sDate.substring(0, 4));
	var sMonth = Number(sDate.substring(5, 7)) - 1;
	var sDay = Number(sDate.substring(8, 10));
	
	var dtDate =  new Date(sYear, sMonth + 1, sDay);
	//var sDueDate = dtDate.getFullYear() + "-" + pad(dtDate.getMonth() + 1, 2) + "-" + pad(dtDate.getDate(), 2);
	var sDueDate = dtDate.getFullYear() + "-" + pad(dtDate.getMonth() + 0, 2) + "-" + pad(dtDate.getDate(), 2);
	document.getElementById("DueDate").value =  sDueDate;
}

function changedItem() {
	if(!this.id.startsWith("RowItemsGuid"))
		return;
	var sGuid = this.id.replace("RowItemsGuid", "");	

	var objItem = null;
	for(var cnt = 0; cnt < arrInventoryItems.length; cnt++) {
		if(arrInventoryItems[cnt]["GUID"] == this.value)
			objItem = arrInventoryItems[cnt];
	}
	if(objItem == null)
		return;
	
	var ctlRowIsTaxed = document.getElementById("RowIsTaxed" + sGuid);
	if(objItem != null & objItem["Is Sales Taxed"] != null && objItem["Is Sales Taxed"].length > 0 && objItem["Is Sales Taxed"][0] == "t")
		ctlRowIsTaxed.checked = true;
	else
		ctlRowIsTaxed.checked = false;
		
	var ctlRowUnitMeasuresGuid = document.getElementById("RowUnitMeasuresGuid" + sGuid);			
	ctlRowUnitMeasuresGuid.value = objItem["Inventory Unit Measures GUID"];

	var ctlRowAccountsGuid = document.getElementById("RowAccountsGuid" + sGuid);
	ctlRowAccountsGuid.value = objItem["Sales Accounts GUID"];

	var ctlRowDescription = document.getElementById("RowDescription" + sGuid);
	if(ctlRowDescription.value == null || ctlRowDescription.value.length == 0)
		ctlRowDescription.value = objItem["Sales Description"];
	else {
		if(confirm("Do you want to overwrite the description?"))
			ctlRowDescription.value = objItem["Sales Description"];
	}

	var ctlRowQuantity = document.getElementById("RowQuantity" + sGuid);
        if(!ctlRowQuantity.value)
		ctlRowQuantity.value = 1;
	var nQty = round(Number(ctlRowQuantity.value), nSettingDocumentQuantityDecimals);
	ctlRowQuantity.value = nQty;

	var ctlRowUnitPrice = document.getElementById("RowUnitPrice" + sGuid);
        if(ctlRowUnitPrice.value && ctlRowUnitPrice.value != 0) {
            if(confirm("Do you want to overwrite the price?"))
                ctlRowUnitPrice.value = objItem["Sales Unit Price"];
        } else
            ctlRowUnitPrice.value = objItem["Sales Unit Price"]; 
 	var nPrice = round(Number(ctlRowUnitPrice.value), nSettingDocumentMoneyDecimals);
	ctlRowUnitPrice.value = nPrice;

	if(!isFinite(nPrice))
		return;	
	
	var nExt = round(nQty * nPrice, nSettingDocumentMoneyDecimals);
	var ctlRowExtension = document.getElementById("RowExtension" + sGuid);
	ctlRowExtension.value = nExt;
	
	invoiceChanged();
}
function changedQuantity() {
	if(!this.id.startsWith("RowQuantity"))
		return;
	var sGuid = this.id.replace("RowQuantity", "");	
	
	var nQty = document.getElementById("RowQuantity" + sGuid).value;
	var nPrice = document.getElementById("RowUnitPrice" + sGuid).value;
	var nExt = document.getElementById("RowExtension" + sGuid).value;

	if(!isFinite(nQty)) {
		window.alert("Quantity is invalid!");
		return;
	}
	
	if(!isFinite(nPrice))
		return;	

	nQty = round(nQty, nSettingDocumentQuantityDecimals);
	nPrice = round(nPrice, nSettingDocumentMoneyDecimals);
        var extdPrice = nQty * nPrice;
	nExt = round(extdPrice, nSettingDocumentMoneyDecimals);
	
	document.getElementById("RowQuantity" + sGuid).value = nQty;
	document.getElementById("RowUnitPrice" + sGuid).value = nPrice;
	document.getElementById("RowExtension" + sGuid).value = nExt;
	
	invoiceChanged();
}
function changedPrice() {
	if(!this.id.startsWith("RowUnitPrice"))
		return;
	var sGuid = this.id.replace("RowUnitPrice", "");	
	
	var nQty = document.getElementById("RowQuantity" + sGuid).value;
	var nPrice = document.getElementById("RowUnitPrice" + sGuid).value;
	var nExt = document.getElementById("RowExtension" + sGuid).value;

	if(!isFinite(nPrice)) {
		window.alert("Unit price is invalid!");
		return;
	}
	
	if(!isFinite(nQty))
		return;	

	nQty = round(nQty, nSettingDocumentQuantityDecimals);
	nPrice = round(nPrice, nSettingDocumentMoneyDecimals);
        var extdPrice = nQty * nPrice;
	nExt = round(extdPrice, nSettingDocumentMoneyDecimals);
	
	document.getElementById("RowQuantity" + sGuid).value = nQty;
	document.getElementById("RowUnitPrice" + sGuid).value = nPrice;
	document.getElementById("RowExtension" + sGuid).value = nExt;
	
	invoiceChanged();
}
function changedExtension() {
	if(!this.id.startsWith("RowExtension"))
		return;
	var sGuid = this.id.replace("RowExtension", "");	
	
	var nQty = document.getElementById("RowQuantity" + sGuid).value;
	var nPrice = document.getElementById("RowUnitPrice" + sGuid).value;
	var nExt = document.getElementById("RowExtension" + sGuid).value;

	if(!isFinite(nExt)) {
		window.alert("Extension is invalid!");
		return;
	}
	
	if(nQty == 0)
		nQty = 1;
	if(!isFinite(nQty))
		return;	
       
	nQty = round(nQty, nSettingDocumentQuantityDecimals);
        var priceEach = nExt / nQty;
	nPrice = round(priceEach, nSettingDocumentMoneyDecimals);
	nExt = new Number(nExt, nSettingDocumentMoneyDecimals);
	
	document.getElementById("RowQuantity" + sGuid).value = nQty;
	document.getElementById("RowUnitPrice" + sGuid).value = nPrice;
	document.getElementById("RowExtension" + sGuid).value = nExt;
	
	invoiceChanged();
}

function invoiceChanged() {
	var nTaxRate = new Number(document.getElementById("TaxRate").value);
	var nTaxable = new Number(0);
	var nNonTaxable = new Number(0);
	var nSubtotal = new Number(0);
	var nTaxes = new Number(0);
	var nTotal = new Number(0);
	
	arrControls = document.getElementsByClassName("RowExtension");
	for (var cnt = 0; cnt < arrControls.length; cnt++) {
		var sGuid = arrControls[cnt].id.replace("RowExtension", "");
		var nAmount = new Number(document.getElementById("RowExtension" + sGuid).value);
		var chkTaxed = document.getElementById("RowIsTaxed" + sGuid);
		if(chkTaxed.checked)
			nTaxable = nTaxable + nAmount;
		else
			nNonTaxable = nNonTaxable + nAmount;
	}
	nSubtotal = nTaxable + nNonTaxable;
	nTaxes = nTaxable * nTaxRate;
	nTotal = nSubtotal + nTaxes;
	
	document.getElementById("TaxableSubtotal").value = round(nTaxable, nSettingDocumentMoneyDecimals);
	document.getElementById("NontaxableSubtotal").value = round(nNonTaxable, nSettingDocumentMoneyDecimals);
	document.getElementById("Subtotal").value = round(nSubtotal, nSettingDocumentMoneyDecimals);
	document.getElementById("Taxes").value = round(nTaxes, nSettingDocumentMoneyDecimals);
	document.getElementById("Total").value = round(nTotal, nSettingDocumentMoneyDecimals);		
}

function pageLoaded() {
	document.getElementById("Date").onchange = changedDate;

	var arrControls = document.getElementsByClassName("RowItemsGuid");
	for (var cnt = 0; cnt < arrControls.length; cnt++)
		arrControls[cnt].onchange = changedItem;
	
	var arrControls = document.getElementsByClassName("RowQuantity");
	for (var cnt = 0; cnt < arrControls.length; cnt++)
		arrControls[cnt].onchange = changedQuantity;

	arrControls = document.getElementsByClassName("RowUnitPrice");
	for (var cnt = 0; cnt < arrControls.length; cnt++)
		arrControls[cnt].onchange = changedPrice;

	arrControls = document.getElementsByClassName("RowExtension");
	for (var cnt = 0; cnt < arrControls.length; cnt++)
		arrControls[cnt].onchange = changedExtension;
	
	arrControls = document.getElementsByClassName("RowIsTaxed");
	for (var cnt = 0; cnt < arrControls.length; cnt++)
		arrControls[cnt].onchange = invoiceChanged;	
	
}