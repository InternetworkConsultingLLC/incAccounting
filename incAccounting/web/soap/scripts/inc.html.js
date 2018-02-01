if(!inc)
	var inc = {};
if(!inc.html)
	inc.html = {};

new function() {
	var baseUrl = "http://localhost:8080/incAccounting";
	var xmlNamespace = "ws";
	var xmlNamesppaceUrl = "http://ws.accounting.internetworkconsulting.net/";
	var nsResolver = function(prefix) {
		var ns = {
			"S": "http://schemas.xmlsoap.org/soap/envelope/",
			"ns2": "http://ws.accounting.internetworkconsulting.net/"
		};
		return ns[prefix] || null;
	};
	
	inc.isNumber = function(value) { return typeof value === 'number' && isFinite(value); };
	inc.isString = function(value) { return typeof value === 'string' || value instanceof String; };
	inc.isArray = function(value) { return value && typeof value === 'object' && value.constructor === Array; };
	inc.isFunction = function(value) { return typeof value === 'function'; };
	inc.isObject = function(value) { return value && typeof value === 'object' && value.constructor === Object; };
	inc.isNull = function(value) { return value === null; };
	inc.isUndefined = function(value) { return typeof value === 'undefined'; };
	inc.isBoolean = function(value) { return typeof value === 'boolean'; };
	inc.isRegExp = function(value) { return value && typeof value === 'object' && value.constructor === RegExp; };
	inc.isError = function(value) { return value instanceof Error && typeof value.message !== 'undefined'; };
	inc.isDate = function(value) { return value instanceof Date; };
	inc.isSymbol = function(value) { return typeof value === 'symbol'; };

	inc.html.Dom = new function() {
		var obj = new Object();
		
		obj.removeClass = function(element, class_name) { element.className = element.className.replace(" " + class_name + " " , ""); };
		obj.addClass = function(element, class_name) { element.className = element.className + " " + class_name + " "; };
		
		return obj;
	};		
	
	inc.html.Ajax = new function() {
		var obj = new Object();

		var toXml = function(obj, nested) {
			var ret = "";
			var bIsNested = false;
			if (typeof nested === "boolean")
				bIsNested = nested;

			if (inc.isNumber(obj) || inc.isString(obj)) {
				return obj.toString();
			} else if (inc.isFunction(obj)) {
				// do nothing			
			} else if (inc.isArray(obj)) {
				for(var index in obj)
					ret += "<entry>" + toXml(obj[index]) + "</entry>";
				return ret;
			} else if (inc.isObject(obj)) {
				if (bIsNested)
					ret += "<" + obj.constructor.name + ">";
				for(var index in obj)
					ret += "<" + index + ">" + toXml(obj[index]) + "</" + index + ">";
				if (bIsNested)
					ret += "</" + obj.prototype.name + ">";
				return ret;
			} else if (inc.isNull(obj) || inc.isUndfined(obj)) {
				return "";
			} else if (inc.isBoolean(obj)) {
				if (obj === true)
					return "true";
				else
					return "false";
			} else if (inc.isDate(obj)) {
				return obj.toISOString();
			} else {
				alert("Invalid type: " + typeof obj);
			}
		};
		var soapify = function(object, method) {
			var ret = "";

			ret += "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:" + xmlNamespace + "=\"" + xmlNamesppaceUrl + "\">";
			ret += "<soapenv:Header/>";
			ret += "<soapenv:Body>";
			ret += "<" + xmlNamespace + ":" + method + ">";
			ret += toXml(object);
			ret += "</" + xmlNamespace + ":" + method + ">";
			ret += "</soapenv:Body>";
			ret += "</soapenv:Envelope>";

			return ret;
		};

		var capitalizeFirstLetter = function(value) {
			return value.charAt(0).toUpperCase() + value.slice(1);
		};
		
		var populateMap = function(xml, map) {
			var key = null;
			var value = null;
			
			for(var cnt = 0; cnt < xml.childNodes.length; cnt++) {
				var current = xml.childNodes[cnt];
				if(current.localName === "key")
					key = current.firstChild.nodeValue;
				else if(current.localName === "value") {
					if(current.firstChild) 
						value = current.firstChild.nodeValue;
				} else
					throw new Error("Invalid local name on entry node: " + current.localName);				
			}

			if(value === null)
				map[key] = null;
			else if(value.toLowerCase() === "true")
				map[key] = true;
			else if(value.toLowerCase() === "false")
				map[key] = false;
			else if(Number.isFinite(value))
				map[key] = Number(value);
			else if(new Date(value) !== "Invalid Date" && !isNaN(new Date(value)))
				map[key] = new Date(value);
			else
				map[key] = value;
		};
		
		obj.populateObject = function(xml, target) {
			for(var cnt = 0; cnt < xml.childNodes.length; cnt++) {
				var current = xml.childNodes[cnt];
				
				if(current.childNodes && current.childNodes.length === 1 && current.childNodes[0].nodeType === Node.TEXT_NODE) {
					// one text child
					var text = current.childNodes[0].nodeValue;
					if(text.toLowerCase() === "true") {
						if(target.hasOwnProperty("set" + capitalizeFirstLetter(current.localName)))
							eval("target.set" + capitalizeFirstLetter(current.localName) + "(true)");
						else
							target[capitalizeFirstLetter(current.localName)] = true;
					} else if(text.toLowerCase() === "false") {
						if(target.hasOwnProperty("set" + capitalizeFirstLetter(current.localName)))
							eval("target.set" + capitalizeFirstLetter(current.localName) + "(false)");
						else
							target[capitalizeFirstLetter(current.localName)] = false;
					} else if(Number.isFinite(text)) {
						if(target.hasOwnProperty("set" + capitalizeFirstLetter(current.localName)))
							eval("target.set" + capitalizeFirstLetter(current.localName) + "(" + text + ")");
						else
							target[capitalizeFirstLetter(current.localName)] = Number(text);
					} else if(new Date(text) !== "Invalid Date" && !isNaN(new Date(text))) {
						if(target.hasOwnProperty("set" + capitalizeFirstLetter(current.localName)))
							eval("target.set" + capitalizeFirstLetter(current.localName) + "(new Date(\"" + text + "\"))");
						else
							target[capitalizeFirstLetter(current.localName)] = new Date(text);
					} else {
						if(target.hasOwnProperty("set" + capitalizeFirstLetter(current.localName))) {
							var invocable = "target.set" + capitalizeFirstLetter(current.localName) + "(\"" + text.replace(/"/g, '\\"') + "\")";
							eval(invocable);
						} else
							target[capitalizeFirstLetter(current.localName)] = text;
					}
				} else if(current.childNodes && current.childNodes.length === 0) {
					// no children = null
					if(current.localName === "changes")
						target["Changes"] = new inc.data.Map();
					else if(target.hasOwnProperty("set" + capitalizeFirstLetter(current.localName)))
						eval("target.set" + capitalizeFirstLetter(current.localName) + "(null)");
					else
						target[capitalizeFirstLetter(current.localName)] = null;
				} else if(current.childNodes && current.childNodes.length > 1) {
					// multiple children					
					// check if their the same - array or map?
					var bSame = true;
					var bLastName = current.childNodes[0].localName;
					for(var childCnt = 1; childCnt < current.childNodes.length; childCnt++)
						bSame = bSame && bLastName === current.childNodes[childCnt].localName;
					
					if(bSame && bLastName === "entry" && (current.localName === "originals" || current.localName === "changes" || current.localName === "columns")) {
						// common map
						var map = new inc.data.Map();
						for(var childCnt = 0; childCnt < current.childNodes.length; childCnt++)
							populateMap(current.childNodes[childCnt], map);
						target[capitalizeFirstLetter(current.localName)] = map;
					} else if(bSame) {
						// array
						var arr = [];
						for(var childCnt = 0; childCnt < current.childNodes.length; childCnt++) {
							var child = new Object();
							obj.populateObject(current.childNodes[childCnt], child);
							arr.push(child);
						}
						target[capitalizeFirstLetter(current.localName)] = arr;
					} else {
						// object
						var child = new Object();
						obj.populateObject(current, child);
						target[capitalizeFirstLetter(current.localName)] = child;
					}
					
				}
			}
		};
		
		obj.getByPath = function(xml_doc, xpath, type) {
			var doc = xml_doc;

			//https://developer.mozilla.org/en-US/docs/Web/JavaScript/Introduction_to_using_XPath_in_JavaScript#XPathResult_Defined_Constants
			//var xpathResult = document.evaluate( xpathExpression, contextNode, namespaceResolver, resultType, result );
			var ret = doc.evaluate(xpath, doc, nsResolver, type, null);

			switch (ret.resultType) {
				case inc.html.Ajax.BOOLEAN_TYPE:
					return ret.booleanValue;
				case inc.html.Ajax.NUMBER_TYPE:
					return ret.numberValue;
				case inc.html.Ajax.STRING_TYPE:
					return ret.stringValue;
				case inc.html.Ajax.ORDERED_NODE_ITERATOR_TYPE:
				case inc.html.Ajax.UNORDERED_NODE_ITERATOR_TYPE:
					var nodes = [];
					var current = ret.iterateNext();
					while (current) {
						nodes.push(current);
						current = ret.iterateNext();
					}
					return nodes;
				default:
					throw new Error("Invalid node type!");
			}
		};
		obj.postSoap = function(service, method, object, callback) {
			$.ajax({
				url: baseUrl + "/" + service,
				type: "POST",
				dataType: "xml",
				data: soapify(object, method),
				processData: false,
				contentType: "text/xml; charset=\"utf-8\"",
				timeout: 5000,
				success: function(data, textStatus, jqXHR) {
					callback(data);
				},
				error: function(jqXHR, textStatus, errorThrown) {
					console.log(textStatus);
					console.log(errorThrown);

					var message = inc.html.Ajax.getByPath(jqXHR.responseXML, "/S:Envelope/S:Body/S:Fault/faultstring/text()", inc.html.Ajax.STRING_TYPE);
					if (typeof message === "string")
						alert(message);
					else
						alert("The SOAP call failed!\n\n" + textStatus + " - " + errorThrown);
				}
			});
		};

		return obj;
	};
	inc.html.Ajax.NUMBER_TYPE = 1; // A result containing a single number. This is useful for example, in an XPath expression using the count() function.
	inc.html.Ajax.STRING_TYPE = 2; // A result containing a single string.
	inc.html.Ajax.BOOLEAN_TYPE = 3; // A result containing a single boolean value. This is useful for example, in an XPath expression using the not() function.
	inc.html.Ajax.UNORDERED_NODE_ITERATOR_TYPE = 4; // A result node-set containing all the nodes matching the expression. The nodes may not necessarily be in the same order that they appear in the document.
	inc.html.Ajax.ORDERED_NODE_ITERATOR_TYPE = 5; // A result node-set containing all the nodes matching the expression. The nodes in the result set are in the same order that they appear in the document.
	inc.html.Ajax.UNORDERED_NODE_SNAPSHOT_TYPE = 6; //A result node-set containing snapshots of all the nodes matching the expression. The nodes may not necessarily be in the same order that they appear in the document.
	inc.html.Ajax.ORDERED_NODE_SNAPSHOT_TYPE = 7; // A result node-set containing snapshots of all the nodes matching the expression. The nodes in the result set are in the same order that they appear in the document.
	inc.html.Ajax.ANY_UNORDERED_NODE_TYPE = 8; // A result node-set containing any single node that matches the expression. The node is not necessarily the first node in the document that matches the expression.
	inc.html.Ajax.FIRST_ORDERED_NODE_TYPE = 9; // A result node-set containing the first node in the document that matches the expression.	
};





