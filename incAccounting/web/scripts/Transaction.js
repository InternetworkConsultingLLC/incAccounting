function pageLoaded() {
	
	var arrControls = document.getElementsByClassName("LineDebit");
	for (var cnt = 0; cnt < arrControls.length; cnt++)
		arrControls[cnt].onchange = debitChanged;

}
function debitChanged() {
    if(!this.id.startsWith("LineDebit"))
	return;
    
    var nBalance = new Number(0);
    arrControls = document.getElementsByClassName("LineDebit");
    for(var cnt = 0; cnt < arrControls.length; cnt++) {
        var nAmount = arrControls[cnt].value;
        if(!isFinite(nAmount)) {
            window.alert("Not a valid debit!");
            return;
        }
        var roundedDebit = round(nAmount, nSettingDocumentMoneyDecimals);
        arrControls[cnt].value = roundedDebit;

        nBalance = nBalance + Number(nAmount);
    }
    var roundedBalance = round(nBalance, nSettingDocumentMoneyDecimals);
    document.getElementById("Balance").value = roundedBalance;
}