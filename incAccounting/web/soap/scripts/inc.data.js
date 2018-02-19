if(!inc)
	var inc = {};
if(!inc.data)
	inc.data = {};

new function() {
	function _inc_data_Map() {};
	inc.data.Map = _inc_data_Map;
	inc.data.isMap = function(value) {
		try {
			return value.__proto__.constructor === _inc_data_Map;		
		}
		catch(error) {
			return false;
		}
	};

	
	inc.data.Row = function() {
		var obj = new Object();

		obj.SqlSecurableGuid = "";
		obj.SqlTableName = "";
		obj.Columns = new inc.data.Map();
		obj.Changes = new inc.data.Map();
		obj.Originals = new inc.data.Map();
		obj.IsDeleted = false;

		obj.getSqlSecurableGuid = function() { return obj.SecurableGuid; };
		obj.setSqlSecurableGuid = function(value) { obj.SecurableGuid = value; };

		obj.getSqlTableName = function() { return obj.SqlTableName; };
		obj.setSqlTableName = function(value) { obj.SqlTableName = value; };

		obj.getColumns = function() { return obj.Columns; };
		obj.setColumns = function(value) { obj.Columns = value; };

		obj.getChanges = function() { return obj.Changes; };
		obj.setChanges = function(value) { obj.Changes = value; };

		obj.getOriginals = function() { return obj.Originals; };
		obj.setOriginals = function(value) { obj.Originals = value; };

		obj.setIsDeleted = function(value) { obj.IsDeleted = value; };
		obj.getIsDeleted = function() { return obj.IsDeleted; };

		obj.get = function(key) {
			var bInChanges = obj.Changes.hasOwnProperty(key);
			var bInOriginals = obj.Originals.hasOwnProperty(key);

			if(bInChanges && typeof obj.Changes[key] !== "undefined")
				return obj.Changes[key];
			else if(bInOriginals && typeof obj.Originals[key] !== "undefined")
				return obj.Originals[key];
			else
				return null;
		};
		obj.set = function(key, value) {
			var bInOriginals = obj.Originals.hasOwnProperty(key);
			var bMatchesOriginal = obj.Originals[key] === value;
			var dateValue = null;
			
			if(bInOriginals && inc.isDate(obj.Originals[key]) && inc.isDate(value)) {
				if(!dateValue instanceof Date)
					dateValue = newDate(value);
				else
					dateValue = value;
				bMatchesOriginal = dateValue.toISOString() === obj.Originals[key].toISOString();
			}
			
			var bInChanges = obj.Changes.hasOwnProperty(key);

			if(bInOriginals && bMatchesOriginal) {
				if(bInChanges)
					delete obj.Changes[key];
			} else
				if(dateValue)
					obj.Changes[key] = dateValue;
				else
					obj.Changes[key] = value;
		};

		obj.reset = function() {
			obj.Changes = new inc.data.Map();
		};

		obj.getRowState = function() {	
			var bNew = Object.keys(obj.Originals).length === 0;
			var bChanged = Object.keys(obj.Changes).length > 0;			

			if(obj.IsDeleted === true && bNew !== true)
				return inc.data.Row.STATE_DELETE;
			else if(bNew === true)
				return inc.data.Row.STATE_INSERT;
			else if(bChanged === true)
				return inc.data.Row.STATE_UPDATE;

			return inc.data.Row.STATE_ORIGINAL;			
		};

		return obj;	
	};
	inc.data.Row.STATE_ORIGINAL = 0;
	inc.data.Row.STATE_INSERT = 1;
	inc.data.Row.STATE_UPDATE = 2;
	inc.data.Row.STATE_DELETE = 3;	
};

