package net.internetworkconsulting.data;

import java.util.HashMap;


public interface RowInterface  {
	public enum RowState { NA, Insert, Update, Delete }

	void initialize() throws Exception;
	
	String getSqlTableName();
	void setSqlTableName(String value);
	
	String getSqlSecurableGuid();
	void setSqlSecurableGuid(String value);
	
	HashMap<String, String> getColumns();
	void setColumns(HashMap<String, String> value);
	
	void setChanges(HashMap<String, Object> value);
	HashMap<String, Object> getChanges();
	void setOriginals(HashMap<String, Object> value);
	HashMap<String, Object> getOriginals();

	RowState getRowState();
	void setIsDeleted(boolean value);
	boolean getIsDeleted();
	void reset();

	boolean set(String name, Object value) throws Exception;
	Object get(String name);
	
	void beforeSave(AdapterInterface adapter) throws Exception;
	void afterSave(AdapterInterface adapter) throws Exception;
}
