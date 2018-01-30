var MenuController = function(title) {
	// private	
	var divMenuOptions = document.getElementById("divMenuOptions");
	var divContent = document.getElementById("divContent");
	var spanMenuTitle = document.getElementById("spanMenuTitle");
	var imgMenuIcon = document.getElementById("imgMenuIcon");
	
	var bVisable = false;
	
	spanMenuTitle.innerHTML = title;

	DomHelper.removeClass(divContent, "hidden");
	DomHelper.addClass(divMenuOptions, "hidden");
				
	imgMenuIcon.onclick = function() {
		if(bVisable) {
			bVisable = false;
			DomHelper.removeClass(divContent, "hidden");
			DomHelper.addClass(divMenuOptions, "hidden");				
		} else {
			bVisable = true;
			DomHelper.removeClass(divMenuOptions, "hidden");
			DomHelper.addClass(divContent, "hidden");				
		}
	};
};
