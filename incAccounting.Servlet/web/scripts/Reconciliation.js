function pageLoaded() {
	var arrControls = document.getElementsByClassName("LineCleared");
	for (var cnt = 0; cnt < arrControls.length; cnt++) {
		if(arrControls[cnt].checked) {
			//              check            td            tr
			var elmParent = arrControls[cnt].parentElement.parentElement;
			var arrChildren = elmParent.childNodes;
			for(var cntChildren = 0; cntChildren < arrChildren.length; cntChildren++) {
				if(arrChildren[cntChildren].nodeName == "TD") {
					arrChildren[cntChildren].className = arrChildren[cntChildren].className.concat(" cleared ");
				}
			}
		}
	}
}
