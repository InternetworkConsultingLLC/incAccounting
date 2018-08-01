package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class SettingsRow extends Row implements SettingsInterface {
	public SettingsRow() { 
		super(); 
		setSqlTableName("settings");
		setSqlSecurableGuid("2e5d8aa3dfa8ef34ca5131d20f9dad51");
	}
	public static String TABLE_NAME = "settings";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String USERS_GUID = "Users GUID";
	public boolean setUsersGuid(java.lang.String value) throws Exception { return set(USERS_GUID, value); }
	public java.lang.String getUsersGuid() { return (java.lang.String) get(USERS_GUID); }
	
	public static String KEY = "Key";
	public boolean setKey(java.lang.String value) throws Exception { return set(KEY, value); }
	public java.lang.String getKey() { return (java.lang.String) get(KEY); }
	
	public static String TYPE = "Type";
	public boolean setType(java.lang.String value) throws Exception { return set(TYPE, value); }
	public java.lang.String getType() { return (java.lang.String) get(TYPE); }
	
	public static String OPTION_QUERY = "Option Query";
	public boolean setOptionQuery(java.lang.String value) throws Exception { return set(OPTION_QUERY, value); }
	public java.lang.String getOptionQuery() { return (java.lang.String) get(OPTION_QUERY); }
	
	public static String VALUE = "Value";
	public boolean setValue(java.lang.String value) throws Exception { return set(VALUE, value); }
	public java.lang.String getValue() { return (java.lang.String) get(VALUE); }
	

	// child loaders
	

	// parent loaders
	
	protected Object rUserParent = null;
	public <T extends UsersRow> T loadUser(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rUserParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"users\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getUsersGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique users row by GUID (" + Statement.convertObjectToString(this.getUsersGuid(), null) + ")!");
			rUserParent = lst.get(0);
		}
		return (T) rUserParent;
	}
	

	// unique key loaders
	
	public static <T extends SettingsRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"settings\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique settings row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
}
