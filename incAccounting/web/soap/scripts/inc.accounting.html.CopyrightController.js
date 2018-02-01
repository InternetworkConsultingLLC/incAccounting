if(!inc)
	var inc = {};
if(!inc.accounting)
	inc.accounting = {};
if(!inc.accounting.html)
	inc.accounting.html = {};

new function() {
	window.onload = function() {
		new inc.accounting.html.MenuController("License");
		new inc.accounting.html.CopyrightController();
	};

	inc.accounting.html.CopyrightController = function() {};
};

