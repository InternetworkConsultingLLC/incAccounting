var baseUrl = "http://localhost:8080/incAccounting";

////////////////////////////////////////////////////////////////////////////////

var DomHelper = (new function() {
	// privates
	return (new function() {
		// publics
		this.removeClass = function(element, class_name) {
			element.className = element.className.replace(" " + class_name + " " , "");
		};
		this.addClass = function(element, class_name) {
			element.className = element.className + " " + class_name + " ";
		};		
	});
});

////////////////////////////////////////////////////////////////////////////////

var XmlHelper = (new function() {	
	var _log_text = "";

	var _log = function(node) {
		if(typeof node === "undefined") 
			return;
		else if(node.nodeType === Node.DOCUMENT_NODE) {
			_log_text += " document { ";
			for(var idx = 0; idx < node.childNodes.length; idx++)
				_log(node.childNodes[idx]);
			_log_text += " } ";
		} else if(node.nodeType === Node.ELEMENT_NODE) {
			_log_text += " " + node.nodeName + " { ";
			for(var idx = 0; idx < node.childNodes.length; idx++)
				_log(node.childNodes[idx]);
			_log_text += " } ";
		} else if(node.nodeType === Node.TEXT_NODE) {
			_log_text += " " + node.nodeValue + " ";
		}
	};
	var _nsResolver = function(prefix) {
		var ns = {
		  "S": "http://schemas.xmlsoap.org/soap/envelope/",
		  "ns2": "http://rest.accounting.internetworkconsulting.net/"
		};
		return ns[prefix] || null;		
	};

	return (new function() {
		this.log = function(document) {
			_log_text = "";
			_log(document);
			console.log(_log_text);
		};
		this.getByPath = function(doc, xpath, type, log) {
			if(log === true)
				this.log(doc, "");		

			//https://developer.mozilla.org/en-US/docs/Web/JavaScript/Introduction_to_using_XPath_in_JavaScript#XPathResult_Defined_Constants
			//var xpathResult = document.evaluate( xpathExpression, contextNode, namespaceResolver, resultType, result );
			var ret = doc.evaluate(xpath, doc, _nsResolver, type, null);

			switch(ret.resultType) {
				case XmlHelper.BOOLEAN_TYPE:
					return ret.booleanValue;
				case XmlHelper.NUMBER_TYPE:
					return ret.numberValue;
				case XmlHelper.STRING_TYPE:
					return ret.stringValue;
				default:
					throw new Error("Invalid node type!");
			}
		};
	});	
});
XmlHelper.NUMBER_TYPE = 1; // A result containing a single number. This is useful for example, in an XPath expression using the count() function.
XmlHelper.STRING_TYPE = 2; // A result containing a single string.
XmlHelper.BOOLEAN_TYPE = 3; // A result containing a single boolean value. This is useful for example, in an XPath expression using the not() function.
XmlHelper.UNORDERED_NODE_ITERATOR_TYPE = 4; // A result node-set containing all the nodes matching the expression. The nodes may not necessarily be in the same order that they appear in the document.
XmlHelper.ORDERED_NODE_ITERATOR_TYPE = 5; // A result node-set containing all the nodes matching the expression. The nodes in the result set are in the same order that they appear in the document.
XmlHelper.UNORDERED_NODE_SNAPSHOT_TYPE = 6; //A result node-set containing snapshots of all the nodes matching the expression. The nodes may not necessarily be in the same order that they appear in the document.
XmlHelper.ORDERED_NODE_SNAPSHOT_TYPE = 7; // A result node-set containing snapshots of all the nodes matching the expression. The nodes in the result set are in the same order that they appear in the document.
XmlHelper.ANY_UNORDERED_NODE_TYPE = 8; // A result node-set containing any single node that matches the expression. The node is not necessarily the first node in the document that matches the expression.
XmlHelper.FIRST_ORDERED_NODE_TYPE = 9; // A result node-set containing the first node in the document that matches the expression.	

////////////////////////////////////////////////////////////////////////////////

var User = function() {
	// private functions
	var lstLogs = null;
	var lstMembers = null;
	var lstSettings = null;
	var objEmployee = null;

	return (new function() {
		// public properties
		this.GUID = ""; // string
		this.IsAllowed = false; // boolean
		this.DisplayName = ""; // string
		this.EmailAddress = ""; // string
		this.PasswordDate = Date.now(); // date time
		this.PasswordHash = ""; // string
		this.EmployeesGUID = ""; // string

		this.Database = ""; // string
		this.Password = ""; // string
		
		this.loadLogs = function(force) {};
		this.loadMemberships = function(force) {};
		this.loadSettings = function(force) {};
		this.loadEmployee = function(force) {};

		this.login = function(callback) {
			$.soap({
				url: baseUrl + "/User",
				appendMethodToURL: false,
				method: "login",
				elementName: "rest:login",
				SOAPAction: "\"\"",
				envAttributes: { "xmlns:rest": "http://rest.accounting.internetworkconsulting.net/" },
				data: {
					EmailAddress: this.EmailAddress,
					Password: this.Password,
					Database: this.Database
				},
				success: function (soapResponse) {
					var ret = XmlHelper.getByPath(soapResponse.toXML(), "/S:Envelope/S:Body/ns2:loginResponse/return/text()", XmlHelper.STRING_TYPE, true);
					callback(ret);
				},
				error: function (soapResponse) {
					callback(soapResponse.httpCode + " - " + soapResponse.httpText);
				}
			});		
		};		
	});
};
User.loadByGuid = function(GUID) {};
User.loadByDisplayName = function(DisplayName) {};
User.loadByEmailAddress = function(EmailAddress) {};

////////////////////////////////////////////////////////////////////////////////

var Report = function() {
	// privates
	var objSecurable = null;
	var lstBlocks = null;
	var lstFilters = null;
	
	return (new function() {
		// publics
		this.Guid = ""; // string
		this.DisplayName = ""; // string
		this.HtmlTemplate = ""; // string
		this.Title = ""; // string
		this.AutoLoad = false; // boolean
		this.Query = ""; // string
		
		this.loadSecurable = function(force) {};
		this.loadBlocks = function(force) {};
		this.loadFilters = function(force) {};
		
		this.generate = function() {};
	});
};
Report.loadByDisplayName = function(DisplayName) {};