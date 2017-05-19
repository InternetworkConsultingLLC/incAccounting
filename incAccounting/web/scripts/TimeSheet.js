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
	var arrChanges = [];	
	for(var originalCnt = 0; originalCnt < arrOriginals.length; originalCnt++) {
		var nOriginalStarted = getDate(arrOriginals[originalCnt].Started);
		var nOriginalEnded = getDate(arrOriginals[originalCnt].Ended);

		var bHandled = false;
		for(var changesCnt = 0; changesCnt < arrChanges.length; changesCnt++) {
			var nChangedStarted = getDate(arrChanges[changesCnt].Started);
			var nChangedEnded = getDate(arrChanges[changesCnt].Ended);
			var nBufferedStarted = nChangedStarted - (5 * 60 * 100);
			var nBufferedEnded = nChangedEnded + (5 * 60 * 100);			
			if(nBufferedStarted <= nOriginalEnded && nBufferedEnded >= nOriginalStarted) {
				if(nOriginalStarted < nChangedStarted)
					arrChanges[changesCnt].Started = arrOriginals[originalCnt].Started;
				if(nOriginalEnded > nChangedEnded)
					arrChanges[changesCnt].Ended = arrOriginals[originalCnt].Ended;
				bHandled = true;
			}
		}
		if(!bHandled) {
			var objNew = [];
			objNew.Started = arrOriginals[originalCnt].Started;
			objNew.Ended = arrOriginals[originalCnt].Ended;
			arrChanges.push(objNew);
		}
	}
	return arrChanges;
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
	
	var sYear = value.substring(0, 4);
	var sMonth = value.substring(5, 7);
	var sDay = value.substring(8, 10);
	var sHour = value.substring(11, 13);
	var sMinute = value.substring(14, 16);
	var sSecond = value.substring(17);
	
	return Date.UTC(sYear, sMonth, sDay, sHour, sMinute, sSecond);
}