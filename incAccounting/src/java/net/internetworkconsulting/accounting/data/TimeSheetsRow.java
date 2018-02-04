/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class TimeSheetsRow extends Row implements TimeSheetsInterface {
	public TimeSheetsRow() { 
		super(); 
		setSqlTableName("time sheets");
		setSqlSecurableGuid("953a91f1711b44161ae782f4093d60f9");
	}
	public static String TABLE_NAME = "time sheets";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String EMPLOYEES_GUID = "Employees GUID";
	public boolean setEmployeesGuid(java.lang.String value) throws Exception { return set(EMPLOYEES_GUID, value); }
	public java.lang.String getEmployeesGuid() { return (java.lang.String) get(EMPLOYEES_GUID); }
	
	public static String NUMBER = "Number";
	public boolean setNumber(java.lang.String value) throws Exception { return set(NUMBER, value); }
	public java.lang.String getNumber() { return (java.lang.String) get(NUMBER); }
	
	public static String PERIOD_ENDING = "Period Ending";
	public boolean setPeriodEnding(java.util.Date value) throws Exception { return set(PERIOD_ENDING, value); }
	public java.util.Date getPeriodEnding() { return (java.util.Date) get(PERIOD_ENDING); }
	
	public static String TOTAL_HOURS = "Total Hours";
	public boolean setTotalHours(java.math.BigDecimal value) throws Exception { return set(TOTAL_HOURS, value); }
	public java.math.BigDecimal getTotalHours() { return (java.math.BigDecimal) get(TOTAL_HOURS); }
	
	public static String PERIOD_STARTING = "Period Starting";
	public boolean setPeriodStarting(java.util.Date value) throws Exception { return set(PERIOD_STARTING, value); }
	public java.util.Date getPeriodStarting() { return (java.util.Date) get(PERIOD_STARTING); }
	

	// child loaders
	
	protected Object lstTimeEntriesChildren = null;
	public <T extends TimeEntriesRow> List<T> loadTimeEntries(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstTimeEntriesChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"time entries\" WHERE \"Time Sheets GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstTimeEntriesChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstTimeEntriesChildren;
	}
	

	// parent loaders
	
	protected Object rEmployeeParent = null;
	public <T extends ContactsRow> T loadEmployee(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rEmployeeParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"contacts\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getEmployeesGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique contacts row by GUID (" + Statement.convertObjectToString(this.getEmployeesGuid(), null) + ")!");
			rEmployeeParent = lst.get(0);
		}
		return (T) rEmployeeParent;
	}
	

	// unique key loaders
	
	public static <T extends TimeSheetsRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"time sheets\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique time sheets row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
	public static <T extends TimeSheetsRow> T loadByNumber(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"time sheets\" WHERE \"Number\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique time sheets row by 'Number': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
}
