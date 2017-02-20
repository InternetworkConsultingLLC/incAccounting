/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.bootstrap.data;

import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class ComputersRow extends Row implements ComputersInterface {
	public ComputersRow() { 
		super(); 
		setSqlTableName("Computers");
		setSqlSecurableGuid("45888a4da062f16a099a7f7c6cc15ee0");
	}
	public static String TABLE_NAME = "Computers";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String DESCRIPTION = "Description";
	public boolean setDescription(java.lang.String value) throws Exception { return set(DESCRIPTION, value); }
	public java.lang.String getDescription() { return (java.lang.String) get(DESCRIPTION); }
	
	public static String MAC_ADDRESS = "MAC Address";
	public boolean setMacAddress(java.lang.String value) throws Exception { return set(MAC_ADDRESS, value); }
	public java.lang.String getMacAddress() { return (java.lang.String) get(MAC_ADDRESS); }
	
	public static String IS_ALLOWED = "Is Allowed";
	public boolean setIsAllowed(java.lang.Boolean value) throws Exception { return set(IS_ALLOWED, value); }
	public java.lang.Boolean getIsAllowed() { return (java.lang.Boolean) get(IS_ALLOWED); }
	

	// child loaders
	
	protected Object lstLogsChildren = null;
	public <T extends LogsRow> List<T> loadLogs(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstLogsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Logs\" WHERE \"Computers GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstLogsChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstLogsChildren;
	}
	

	// parent loaders
	

	// unique key loaders
	
	public static <T extends ComputersRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"Computers\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique Computers row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	

	// load all
	public static <T extends ComputersRow> List<T> loadAll(AdapterInterface adapter, Class model) throws Exception {
		Statement stmt = new Statement("SELECT * FROM \"Logs\"");
		return (List<T>) adapter.load(model, stmt);
	}
}
