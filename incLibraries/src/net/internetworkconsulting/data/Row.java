package net.internetworkconsulting.data;

import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;

public class Row implements RowInterface, Serializable {
	public Row() {
		mapOriginals = new java.util.HashMap<>();
		mapChanges = new java.util.HashMap<>();
	}

	public RowState getRowState() {
		boolean bNew = mapOriginals.isEmpty();
		boolean bChanged = !mapChanges.isEmpty();
		boolean bDeleted = myIsDeleted;

		if(bDeleted && !bNew)
			return RowState.Delete;
		else if(bNew)
			return RowState.Insert;
		else if(bChanged)
			return RowState.Update;

		return RowState.NA;
	}

	private java.util.HashMap<String, Object> mapOriginals;
	public java.util.HashMap<String, Object> getOriginals() {
		return mapOriginals;
	}
	public void setOriginals(java.util.HashMap<String, Object> value) {
		mapOriginals = value;
	}

	private java.util.HashMap<String, Object> mapChanges;
	public java.util.HashMap<String, Object> getChanges() {
		return mapChanges;
	}
	public void setChanges(java.util.HashMap<String, Object> value) {
		mapChanges = value;
	}

	public Object get(String name) {
		if(mapChanges.containsKey(name))
			return mapChanges.get(name);
		else if(mapOriginals.containsKey(name))
			return mapOriginals.get(name);
		else
			return null;
	}
	public boolean set(String name, Object value) throws Exception {
		// original		changes		action
		// !=			!=			add to changes
		// =			!=			remove from changes
		// !=			=			nothing
		// =			=			remove from changes

		Object original = mapOriginals.containsKey(name) ? mapOriginals.get(name) : null;
		Object change = mapChanges.containsKey(name) ? mapChanges.get(name) : null;

		boolean isChanged = false;
		boolean isOriginal =  false;

		if(original instanceof BigDecimal && value instanceof BigDecimal) {
			BigDecimal dOriginal = (BigDecimal) original;
			BigDecimal dValue = (BigDecimal) value;			
			isOriginal = dValue.compareTo(dOriginal) == 0;
		} else {
			isOriginal = mapOriginals.containsKey(name) && (
						(original == null && value == null) || (original != null && value != null && original.equals(value))
					);		
		}
		
		if(change instanceof BigDecimal && value instanceof BigDecimal) {
			BigDecimal dChanged = (BigDecimal) change;
			BigDecimal dValue = (BigDecimal) value;			
			isChanged = dValue.compareTo(dChanged) == 0;
		} else {
			isChanged = mapChanges.containsKey(name) && (
						(change == null && value == null) || (change != null && value != null && change.equals(value))
					);
		}

		if(!isOriginal && !isChanged) {
			mapChanges.put(name, value);
			return true;
		} else if(isOriginal && !isChanged) {
			mapChanges.remove(name);
			return true;
		} else if(!isOriginal && isChanged)
			return false;
		else if(isOriginal && isChanged) {
			mapChanges.remove(name);
			return false;
		}

		// since we accouinted for all cases,
		// we should NEVER get here
		return false;
	}

	public static String IS_DELETED = "Is Deleted";
	private boolean myIsDeleted = false;
	public boolean getIsDeleted() {
		return myIsDeleted;
	}
	public void setIsDeleted(boolean value) {
		myIsDeleted = value;
	}

	public void reset() {
		myIsDeleted = false;
		mapChanges = new java.util.HashMap<>();
	}

	private String sTableName;
	public String getSqlTableName() {
		return sTableName;
	}
	public void setSqlTableName(String value) {
		sTableName = value;
	}

	private String sSqlSecurableGuid = null;
	public String getSqlSecurableGuid() {
		return sSqlSecurableGuid;
	}
	public void setSqlSecurableGuid(String value) {
		sSqlSecurableGuid = value;
	}

	HashMap<String, String> hsColumns = new HashMap<String, String>();
	public HashMap<String, String> getColumns() {
		return hsColumns;
	}
	public void setColumns(HashMap<String, String> value) {
		hsColumns = value;
	}

	public static String getReource(Class cls, String file) throws Exception {
		InputStream is = cls.getResourceAsStream(file);
		return Helper.InputStreamToString(is);
	}

	public void beforeSave(AdapterInterface adapter) throws Exception {
	}
	public void afterSave(AdapterInterface adapter) throws Exception {
	}

	public void initialize() throws Exception { }
}
