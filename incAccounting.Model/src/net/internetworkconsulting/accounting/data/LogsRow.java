/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class LogsRow extends Row implements LogsInterface {
	public LogsRow() { 
		super(); 
		setSqlTableName("Logs");
		setSqlSecurableGuid("b2d37ae1cedf42ff874289b721860af2");
	}
	public static String TABLE_NAME = "Logs";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String OCCURED = "Occured";
	public boolean setOccured(java.sql.Timestamp value) throws Exception { return set(OCCURED, value); }
	public java.sql.Timestamp getOccured() { return (java.sql.Timestamp) get(OCCURED); }
	
	public static String USERS_GUID = "Users GUID";
	public boolean setUsersGuid(java.lang.String value) throws Exception { return set(USERS_GUID, value); }
	public java.lang.String getUsersGuid() { return (java.lang.String) get(USERS_GUID); }
	
	public static String CODE_GUID = "Code GUID";
	public boolean setCodeGuid(java.lang.String value) throws Exception { return set(CODE_GUID, value); }
	public java.lang.String getCodeGuid() { return (java.lang.String) get(CODE_GUID); }
	
	public static String LOG = "Log";
	public boolean setLog(java.lang.String value) throws Exception { return set(LOG, value); }
	public java.lang.String getLog() { return (java.lang.String) get(LOG); }
	
	public static String DETAILS = "Details";
	public boolean setDetails(java.lang.String value) throws Exception { return set(DETAILS, value); }
	public java.lang.String getDetails() { return (java.lang.String) get(DETAILS); }
	

	// child loaders
	

	// parent loaders
	
	protected Object rUserParent = null;
	public <T extends UsersRow> T loadUser(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rUserParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Users\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getUsersGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique Users row by GUID (" + Statement.convertObjectToString(this.getUsersGuid(), null) + ")!");
			rUserParent = lst.get(0);
		}
		return (T) rUserParent;
	}
	

	// unique key loaders
	
	public static <T extends LogsRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"Logs\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique Logs row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	

	// load all
	public static <T extends LogsRow> List<T> loadAll(AdapterInterface adapter, Class model) throws Exception {
		Statement stmt = new Statement("SELECT * FROM \"Logs\"");
		return (List<T>) adapter.load(model, stmt, true);
	}
}
