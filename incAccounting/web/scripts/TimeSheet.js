//function ignoreerror() { return true; }
//window.onerror=ignoreerror();

function pageLoaded() {
	var arrControls = document.getElementsByClassName("LineIsIncluded");
	for (var cnt = 0; cnt < arrControls.length; cnt++)
		arrControls[cnt].onchange = changedCheck;
}
function changedCheck() {	
	var arrTimeSpans = [];	
	var arrControls = document.getElementsByClassName("LineIsIncluded");
	for(var cnt = 0; cnt < arrControls.length; cnt++)
		if(arrControls[cnt].checked)
			arrTimeSpans.push(getTimeEntryByGuid(arrControls[cnt].id.replace("LineIsIncluded", "")));

	var nCount = 0;
	while(arrTimeSpans.length != nCount) {
		nCount = arrTimeSpans.length;
		arrTimeSpans = simplifyTimeEntries(arrTimeSpans);
	}
	
	var iHours = 0;
	for(var cnt = 0; cnt < arrTimeSpans.length; cnt++)
		iHours = iHours + getDate(arrTimeSpans[cnt].Ended) - getDate(arrTimeSpans[cnt].Started);
	iHours = iHours / (60 * 60 * 1000);

	var txtTotalHours = document.getElementById("TotalHours");
	txtTotalHours.value = iHours;
}

function simplifyTimeEntries(arrOriginals) {
	var arrSimplified = [];	
	for(var originalCnt = 0; originalCnt < arrOriginals.length; originalCnt++) {
		var nOriginalStarted = arrOriginals[originalCnt].Started;
		var nOriginalEnded = arrOriginals[originalCnt].Ended;

		var bHandled = false;
		for(var changesCnt = 0; changesCnt < arrSimplified.length; changesCnt++) {
			var nSimplifiedStarted = arrSimplified[changesCnt].Started;
			var nSimplifiedEnded = arrSimplified[changesCnt].Ended;
			
			if(nSimplifiedStarted <= nOriginalStarted && nOriginalStarted <= nSimplifiedEnded) {
				if(nOriginalEnded > nSimplifiedEnded) {
					arrSimplified[changesCnt].Ended = arrOriginals[originalCnt].Ended;
					bHandled = true;
				}
			}
			if(nSimplifiedStarted <= nOriginalEnded && nOriginalEnded <= nSimplifiedEnded) {
				if(nOriginalStarted < nSimplifiedStarted) {
					arrSimplified[changesCnt].Started = arrOriginals[originalCnt].Started;
					bHandled = true;
				}
			}
		}
		if(!bHandled) {
			var objNew = [];
			objNew.Started = arrOriginals[originalCnt].Started;
			objNew.Ended = arrOriginals[originalCnt].Ended;
			arrSimplified.push(objNew);
		}
	}
	return arrSimplified;
}

function getTimeEntryByGuid(guid) {
	for(var cnt = 0; cnt < arrTimeEntries.length; cnt++) {
		if(arrTimeEntries[cnt]["GUID"].toLowerCase() == guid.toLowerCase())
			return arrTimeEntries[cnt];
	}
	
	return null;
}
function getDate(value) {
	// 012345678901234567890
	// 2017-05-15 12:07:00
	
	var sYear = Number(value.substring(0, 4));
	var sMonth = Number(value.substring(5, 7));
	var sDay = Number(value.substring(8, 10));
	var sHour = Number(value.substring(11, 13));
	var sMinute = Number(value.substring(14, 16));
	var sSecond = Number(value.substring(17));
	
	return Date.UTC(sYear, sMonth, sDay, sHour, sMinute, sSecond);
}