if(!inc)
	var inc = {};
if(!inc.html)
	inc.html = {};

Number.isInteger = Number.isInteger || function(value) {
	// polyfill for IE
    return typeof value === "number" && isFinite(value) && Math.floor(value) === value;
};

Number.isFinite = Number.isFinite || function(value) {
	// polyfill for IE
    return typeof value === 'number' && isFinite(value);
};

new function() {
	var baseUrl = "http://localhost:8080/incAccounting";
	var xmlNamespace = "ws";
	var xmlNamesppaceUrl = "http://ws.accounting.internetworkconsulting.net/";
	
	inc.isNumber = function(value) { return typeof value === 'number' && isFinite(value); };
	inc.isString = function(value) { return typeof value === 'string' || value instanceof String; };
	inc.isArray = function(value) { return value && typeof value === 'object' && value.constructor === Array; };
	inc.isFunction = function(value) { 
		if(typeof value === 'function')
			return true;
		if(value.toString().indexOf("function(") > 0)
			return true;
		
		return false;
	};
	inc.isObject = function(value) { return value && typeof value === 'object' && value.constructor === Object; };
	inc.isNull = function(value) { return value === null; };
	inc.isUndefined = function(value) { return typeof value === 'undefined'; };
	inc.isBoolean = function(value) { return typeof value === 'boolean'; };
	inc.isRegExp = function(value) { return value && typeof value === 'object' && value.constructor === RegExp; };
	inc.isError = function(value) { return value instanceof Error && typeof value.message !== 'undefined'; };
	inc.isDate = function(value) {
		if(value instanceof Date)
			return true;
		return inc.Date.isValid(value);
	};
	inc.isSymbol = function(value) { return typeof value === 'symbol'; };

	var zeroPad = function(value, size) {
		var ret = value.toString();
		while(ret.length < size)
			ret = "0" + ret;
		return ret;
	};
	inc.Date = Date;
	inc.Date.isValid = function(value) {
		try { inc.Date.parse(value); }
		catch(err) { return false; }

		return true;
	}
	inc.Date.parse = function(value) {
		var iYear = 1970;
		var iMonth = 0;
		var iDay = 1;
		var iHour = 0;
		var iMinute = 0;
		var iSecond = 0;
		var iZoneSign = 0;
		var iZoneHours = 0;
		var iZoneMinutes = 0;

		var ret = null;

		var regEx = null;
		var regEx2 = null;

		regEx = /^[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}[\+-][0-9]{2}:[0-9]{2}$/;
		regEx2 = /^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}[\+-][0-9]{2}:[0-9]{2}$/;
		if(regEx.test(value) || regEx2.test(value)) {
			// 0         1         2
			// 0123456789012345678901234
			// 2018-01-17T00:00:00-06:00
			// 2018-01-17 00:00:00-06:00

			iYear = Number(value.substring(0, 4));
			iMonth = Number(value.substring(5, 7)) - 1; // take 0 - 11
			iDay = Number(value.substring(8, 10));
			iHour = Number(value.substring(11, 13));
			iMinute = Number(value.substring(14, 16));
			iSecond = Number(value.substring(17, 19));
			iZoneSign = value.substring(19, 20);
			iZoneHours = Number(value.substring(20, 22));
			iZoneMinutes = Number(value.substring(23, 25));

			if(iZoneSign === '-')
				ret = new Date(Date.UTC(iYear, iMonth, iDay, iHour + iZoneHours, iMinute + iZoneMinutes, iSecond));
			else
				ret = new Date(Date.UTC(iYear, iMonth, iDay, iHour - iZoneHours, iMinute - iZoneMinutes, iSecond));
			return ret;
		};
		
		regEx = /^[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}Z$/;
		regEx2 = /^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}Z$/;
		if(regEx.test(value) || regEx2.test(value)) {
			// 0         1         2
			// 0123456789012345678901234
			// 2018-01-17T00:00:00Z
			// 2018-01-17 00:00:00Z

			iYear = Number(value.substring(0, 4));
			iMonth = Number(value.substring(5, 7)) - 1; // take 0 - 11
			iDay = Number(value.substring(8, 10));
			iHour = Number(value.substring(11, 13));
			iMinute = Number(value.substring(14, 16));
			iSecond = Number(value.substring(17, 19));

			return new Date(Date.UTC(iYear, iMonth, iDay, iHour, iMinute, iSecond));
			return ret;
		};		

		regEx = /^[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}$/;
		regEx2 = /^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$/;
		if(regEx.test(value) || regEx2.test(value)) {
			// NO TIME ZONE - ASSUME LOCAL
			// 0         1         2
			// 0123456789012345678901234
			// 2018-01-17T00:00:00
			// 2018-01-17 00:00:00
			
			iYear = Number(value.substring(0, 4));
			iMonth = Number(value.substring(5, 7)) - 1; // take 0 - 11
			iDay = Number(value.substring(8, 10));
			iHour = Number(value.substring(11, 13));
			iMinute = Number(value.substring(14, 16));
			iSecond = Number(value.substring(17, 19));
			iZoneSign = value.substring(19, 20);
			iZoneHours = Math.floor((new Date()).getTimezoneOffset() / 60);
			iZoneMinutes = (new Date()).getTimezoneOffset() - iZoneHours * 60;

			ret = new Date(Date.UTC(iYear, iMonth, iDay, iHour + iZoneHours, iMinute + iZoneMinutes, iSecond));
			return ret;
		};

		regEx = /^[0-9]{4}-[0-9]{2}-[0-9]{2}[\+-][0-9]{2}:[0-9]{2}$/;
		if(regEx.test(value)) {
			// 0         1         2
			// 0123456789012345678901234
			// 2018-01-17-06:00
			iYear = Number(value.substring(0, 4));
			iMonth = Number(value.substring(5, 7)) - 1; // take 0 - 11
			iDay = Number(value.substring(8, 10));
			iZoneSign = value.substring(19, 20);
			iZoneHours = Number(value.substring(20, 22));
			iZoneMinutes = Number(value.substring(23, 25));

			if(iZoneSign === '-')
				ret = new Date(Date.UTC(iYear, iMonth, iDay, iHour + iZoneHours, iMinute + iZoneMinutes, iSecond));
			else
				ret = new Date(Date.UTC(iYear, iMonth, iDay, iHour - iZoneHours, iMinute - iZoneMinutes, iSecond));
			return ret;
		};

		regEx = /^[0-9]{4}-[0-9]{2}-[0-9]{2}$/;
		if(regEx.test(value)) {
			// NO TIME ZONE - ASSUME LOCAL
			// 0         1         2
			// 0123456789012345678901234
			// 2018-01-17

			iYear = Number(value.substring(0, 4));
			iMonth = Number(value.substring(5, 7)) - 1; // take 0 - 11
			iDay = Number(value.substring(8, 10));
			iZoneSign = value.substring(19, 20);
			iZoneHours = Math.floor((new Date()).getTimezoneOffset() / 60);
			iZoneMinutes = (new Date()).getTimezoneOffset() - iZoneHours * 60;

			ret = new Date(Date.UTC(iYear, iMonth, iDay, iHour + iZoneHours, iMinute + iZoneMinutes, iSecond));
			return ret;
		};
		
		
		regEx = /^[0-9]{4}-[0-9]{2}-[0-9]{2}Z$/;
		if(regEx.test(value)) {
			// 0         1         2
			// 0123456789012345678901234
			// 2018-01-17Z

			iYear = Number(value.substring(0, 4));
			iMonth = Number(value.substring(5, 7)) - 1; // take 0 - 11
			iDay = Number(value.substring(8, 10));

			return new Date(Date.UTC(iYear, iMonth, iDay, iHour, iMinute, iSecond));
			return ret;
		};			
		
		throw new Error("Invalid date format '" + value + "'!");
	};
	inc.Date.toIsoDate = function(value) {
		var sYear = value.getFullYear().toString();
		var sMonth = zeroPad(value.getMonth() + 1, 2);
		var sDay = zeroPad(value.getDate(), 2);
		var ret = sYear + "-" + sMonth + "-" + sDay; 
		return ret;
	};

	inc.html.Dom = new function() {
		var obj = new Object();
		
		obj.findGetParameter = function(parameter_name) {
			var tmp = [];
			var sLocation = window.location.toString();
			
			var arrUrl = sLocation.split("?");
			if(arrUrl.length === 2) {
				var kvPairs = arrUrl[1].split("&");
				for(var cnt = 0; cnt < kvPairs.length; cnt++) {
					var parts = kvPairs[cnt].split("=");
					if(parts.length === 2 && parts[0] === parameter_name)
						tmp.push(decodeURIComponent(parts[1]));
				}
			}

			return tmp;
		};		
		obj.removeClass = function(element, class_name) { element.className = element.className.replace(" " + class_name + " " , ""); };
		obj.addClass = function(element, class_name) { element.className = element.className + " " + class_name + " "; };
		
		return obj;
	};		
	
	inc.html.Ajax = new function() {
		var obj = new Object();

		var getXmlType = function(obj) {
			if(Number.isInteger(obj))
				return " type=\"xsd:long\"";
			else if(Number.isFinite(obj))
				return " type=\"xsd:decimal\"";
			else if(inc.isBoolean(obj))
				return " type=\"xsd:boolean\"";
			else if(inc.isDate(obj))
				return " type=\"xsd:dateTime\"";
			else if(inc.isString(obj))
				return " type=\"xsd:string\"";
			else
				return "";
		};
		var toXml = function(obj, nested) {
			var ret = "";
			var bIsNested = false;
			if (typeof nested === "boolean")
				bIsNested = nested;

			if (inc.isNumber(obj) || inc.isString(obj)) {
				return obj.toString();
			} else if(inc.isFunction(obj)) {
				return "";			
			} else if (inc.isArray(obj)) {
				for(var index in obj)
					ret += "<entry>" + toXml(obj[index]) + "</entry>";
				return ret;
			} else if(inc.data.isMap(obj)) {
				for(var index in obj) {
					ret += "<entry>";
					ret += "<key>" + index + "</key>";
					if(!inc.isNull(obj[index]) && !inc.isUndefined(obj[index]))
						ret += "<value" + getXmlType(obj[index]) + ">" + toXml(obj[index]) + "</value>";
					else
						ret += "<value />";
					ret += "</entry>";
				}
				return ret;
			} else if (inc.isObject(obj)) {
				for(var index in obj) {
					if(inc.data.isMap(obj[index]) && Object.keys(obj[index]).length < 1) {
						if(bIsNested)
							ret += "<" + lowerCaseFirstLetter(index) + " />";
						else
							ret += "<" + index + " />";
					} else if(!inc.isNull(obj[index]) && !inc.isUndefined(obj[index]) && !inc.isFunction(obj[index])) {
						if(bIsNested)
							ret += "<" + lowerCaseFirstLetter(index) + getXmlType(obj[index]) + ">" + toXml(obj[index], true) + "</" + lowerCaseFirstLetter(index) + ">";
						else
							ret += "<" + index + getXmlType(obj[index]) + ">" + toXml(obj[index], true) + "</" + index + ">";
					}
				}
				return ret;
			} else if (inc.isNull(obj) || inc.isUndefined(obj)) {
				return "";
			} else if (inc.isBoolean(obj)) {
				if (obj === true)
					return "true";
				else
					return "false";
			} else if (inc.isDate(obj)) {
				return obj.toISOString();
			} else {
				throw new Error("Invalid type: " + typeof obj + "\nTo String: " + obj.toString());
			}
		};
		var soapify = function(object, method) {
			var ret = "";

			ret += "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:" + xmlNamespace + "=\"" + xmlNamesppaceUrl + "\">";
			//ret += "<soapenv:Header/>";
			ret += "<S:Body>";
			ret += "<" + xmlNamespace + ":" + method + ">";
			ret += toXml(object);
			ret += "</" + xmlNamespace + ":" + method + ">";
			ret += "</S:Body>";
			ret += "</S:Envelope>";

			return ret;
		};

		var capitalizeFirstLetter = function(value) {
			return value.charAt(0).toUpperCase() + value.slice(1);
		};
		var lowerCaseFirstLetter = function(value) {
			return value.charAt(0).toLowerCase() + value.slice(1);
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
			else if(inc.isDate(value))
				map[key] = inc.Date.parse(value);
			else
				map[key] = value;
		};
		
		obj.populateObject = function(xml, target) {
			if(!xml)
				throw new Error("The 'xml' is not defind!");
			
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
					} else if(inc.Date.isValid(text)) {
						if(target.hasOwnProperty("set" + capitalizeFirstLetter(current.localName)))
							eval("target.set" + capitalizeFirstLetter(current.localName) + "(inc.Date.parse(\"" + text + "\"))");
						else
							target[capitalizeFirstLetter(current.localName)] = inc.Date.parse(text);
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
					else if(current.localName === "originals")
						target["Originals"] = new inc.data.Map();
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
		
		obj.getNodesByPath = function(xml_node, target_path, parent_paths) {
			var ret = [];
			
			var path = "";
			if(typeof parent_paths === "string")
				path = parent_paths;
			
			if(!xml_node)
				throw new Error("XML node required to get node by path!");
			
			var arrNodes = xml_node.childNodes;
			for(var cnt = 0; cnt < arrNodes.length; cnt++) {
				var currentNode = arrNodes[cnt];
				var currentPath = path + "/" + currentNode.nodeName;
				if(currentPath === target_path)
					ret.push(currentNode);
				else if(target_path.split("/").length > currentPath.split("/").length)
					ret = ret.concat(obj.getNodesByPath(currentNode, target_path, currentPath));
			}
			
			return ret;
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
					var arrNodes = inc.html.Ajax.getNodesByPath(jqXHR.responseXML, "/S:Envelope/S:Body/S:Fault/faultstring/#text");
					if(!arrNodes)
						throw new Error("The SOAP call resuted in an error '" + jqXHR.statusText + "'!");
					else if(arrNodes.length === 1)
						throw new Error("The SOAP call returned an exception!\n\n" + arrNodes[0].nodeValue);
					else
						throw new Error("The SOAP call failed!\n\n" + textStatus + " - " + errorThrown);
				}
			});
		};

		return obj;
	};
};





