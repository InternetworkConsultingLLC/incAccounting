if(!inc)
	var inc = {};
if(!inc.accounting)
	inc.accounting = {};
if(!inc.accounting.html)
	inc.accounting.html = {};

new function() {
	window.onload = function() {
		new inc.accounting.html.Menu("License");
		new inc.accounting.html.Copyright();
	};

	inc.accounting.html.Copyright = function() {};
};

