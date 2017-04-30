/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class TimeEntryTypesRow extends Row implements TimeEntryTypesInterface {
	public TimeEntryTypesRow() { 
		super(); 
		setSqlTableName("time entry types");
		setSqlSecurableGuid("cae6d3b208d93f1fc8ddb0059e278804");
	}
	public static String TABLE_NAME = "time entry types";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String DESCRIPTION = "Description";
	public boolean setDescription(java.lang.String value) throws Exception { return set(DESCRIPTION, value); }
	public java.lang.String getDescription() { return (java.lang.String) get(DESCRIPTION); }
	
	public static String PAYABLE = "Payable";
	public boolean setPayable(java.lang.Boolean value) throws Exception { return set(PAYABLE, value); }
	public java.lang.Boolean getPayable() { return (java.lang.Boolean) get(PAYABLE); }
	

	// child loaders
	
	protected Object lstTimeEntriesChildren = null;
	public <T extends TimeEntriesRow> List<T> loadTimeEntries(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstTimeEntriesChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"time entries\" WHERE \"Entry Types GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstTimeEntriesChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstTimeEntriesChildren;
	}
	

	// parent loaders
	

	// unique key loaders
	
	public static <T extends TimeEntryTypesRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"time entry types\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique time entry types row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
	public static <T extends TimeEntryTypesRow> T loadByDescription(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"time entry types\" WHERE \"Description\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique time entry types row by 'Description': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
}
