if(!inc)
	var inc = {};
if(!inc.accounting)
	inc.accounting = {};
if(!inc.accounting.html)
	inc.accounting.html = {};

new function() {
	inc.accounting.html.MenuController = function(title) {
		// private	
		var divMenuOptions = document.getElementById("divMenuOptions");
		var divContent = document.getElementById("divContent");
		var spanMenuTitle = document.getElementById("spanMenuTitle");
		var imgMenuIcon = document.getElementById("imgMenuIcon");

		var bVisable = false;

		spanMenuTitle.innerHTML = title;
		document.title = "incAccounting - " + title;

		inc.html.Dom.removeClass(divContent, "hidden");
		inc.html.Dom.addClass(divMenuOptions, "hidden");

		imgMenuIcon.onclick = function() {
			if (bVisable) {
				bVisable = false;
				inc.html.Dom.removeClass(divContent, "hidden");
				inc.html.Dom.addClass(divMenuOptions, "hidden");
			} else {
				bVisable = true;
				inc.html.Dom.addClass(divContent, "hidden");
				inc.html.Dom.removeClass(divMenuOptions, "hidden");
			}
		};
	};
};

