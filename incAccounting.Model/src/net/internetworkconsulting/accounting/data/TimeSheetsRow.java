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
	
	public static String USERS_GUID = "Users GUID";
	public boolean setUsersGuid(java.lang.String value) throws Exception { return set(USERS_GUID, value); }
	public java.lang.String getUsersGuid() { return (java.lang.String) get(USERS_GUID); }
	
	public static String EMPLOYEES_GUID = "Employees GUID";
	public boolean setEmployeesGuid(java.lang.String value) throws Exception { return set(EMPLOYEES_GUID, value); }
	public java.lang.String getEmployeesGuid() { return (java.lang.String) get(EMPLOYEES_GUID); }
	
	public static String PAYROLL_CHECKS_GUID = "Payroll Checks GUID";
	public boolean setPayrollChecksGuid(java.lang.String value) throws Exception { return set(PAYROLL_CHECKS_GUID, value); }
	public java.lang.String getPayrollChecksGuid() { return (java.lang.String) get(PAYROLL_CHECKS_GUID); }
	

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
	
	protected Object rPayrollCheckParent = null;
	public <T extends PayrollChecksRow> T loadPayrollCheck(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rPayrollCheckParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"payroll checks\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getPayrollChecksGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique payroll checks row by GUID (" + Statement.convertObjectToString(this.getPayrollChecksGuid(), null) + ")!");
			rPayrollCheckParent = lst.get(0);
		}
		return (T) rPayrollCheckParent;
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
	
}
