/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class TimeEntriesRow extends Row implements TimeEntriesInterface {
	public TimeEntriesRow() { 
		super(); 
		setSqlTableName("time entries");
		setSqlSecurableGuid("48ea885d9d11f339d943378ce30def9c");
	}
	public static String TABLE_NAME = "time entries";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String ENTRY_TYPES_GUID = "Entry Types GUID";
	public boolean setEntryTypesGuid(java.lang.String value) throws Exception { return set(ENTRY_TYPES_GUID, value); }
	public java.lang.String getEntryTypesGuid() { return (java.lang.String) get(ENTRY_TYPES_GUID); }
	
	public static String USERS_GUID = "Users GUID";
	public boolean setUsersGuid(java.lang.String value) throws Exception { return set(USERS_GUID, value); }
	public java.lang.String getUsersGuid() { return (java.lang.String) get(USERS_GUID); }
	
	public static String EMPLOYEES_GUID = "Employees GUID";
	public boolean setEmployeesGuid(java.lang.String value) throws Exception { return set(EMPLOYEES_GUID, value); }
	public java.lang.String getEmployeesGuid() { return (java.lang.String) get(EMPLOYEES_GUID); }
	
	public static String CONTACTS_GUID = "Contacts GUID";
	public boolean setContactsGuid(java.lang.String value) throws Exception { return set(CONTACTS_GUID, value); }
	public java.lang.String getContactsGuid() { return (java.lang.String) get(CONTACTS_GUID); }
	
	public static String TIME_SHEETS_GUID = "Time Sheets GUID";
	public boolean setTimeSheetsGuid(java.lang.String value) throws Exception { return set(TIME_SHEETS_GUID, value); }
	public java.lang.String getTimeSheetsGuid() { return (java.lang.String) get(TIME_SHEETS_GUID); }
	
	public static String DOCUMENTS_GUID = "Documents GUID";
	public boolean setDocumentsGuid(java.lang.String value) throws Exception { return set(DOCUMENTS_GUID, value); }
	public java.lang.String getDocumentsGuid() { return (java.lang.String) get(DOCUMENTS_GUID); }
	
	public static String JOBS_GUID = "Jobs GUID";
	public boolean setJobsGuid(java.lang.String value) throws Exception { return set(JOBS_GUID, value); }
	public java.lang.String getJobsGuid() { return (java.lang.String) get(JOBS_GUID); }
	
	public static String DEPARTMENTS_GUID = "Departments GUID";
	public boolean setDepartmentsGuid(java.lang.String value) throws Exception { return set(DEPARTMENTS_GUID, value); }
	public java.lang.String getDepartmentsGuid() { return (java.lang.String) get(DEPARTMENTS_GUID); }
	
	public static String STARTED = "Started";
	public boolean setStarted(java.sql.Timestamp value) throws Exception { return set(STARTED, value); }
	public java.sql.Timestamp getStarted() { return (java.sql.Timestamp) get(STARTED); }
	
	public static String ENDED = "Ended";
	public boolean setEnded(java.sql.Timestamp value) throws Exception { return set(ENDED, value); }
	public java.sql.Timestamp getEnded() { return (java.sql.Timestamp) get(ENDED); }
	
	public static String DESCRIPTION = "Description";
	public boolean setDescription(java.lang.String value) throws Exception { return set(DESCRIPTION, value); }
	public java.lang.String getDescription() { return (java.lang.String) get(DESCRIPTION); }
	

	// child loaders
	

	// parent loaders
	
	protected Object rTimeEntryTypeParent = null;
	public <T extends TimeEntryTypesRow> T loadTimeEntryType(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rTimeEntryTypeParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"time entry types\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getEntryTypesGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique time entry types row by GUID (" + Statement.convertObjectToString(this.getEntryTypesGuid(), null) + ")!");
			rTimeEntryTypeParent = lst.get(0);
		}
		return (T) rTimeEntryTypeParent;
	}
	
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
	public <T extends EmployeesRow> T loadEmployee(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rEmployeeParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"employees\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getEmployeesGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique employees row by GUID (" + Statement.convertObjectToString(this.getEmployeesGuid(), null) + ")!");
			rEmployeeParent = lst.get(0);
		}
		return (T) rEmployeeParent;
	}
	
	protected Object rContactParent = null;
	public <T extends ContactsRow> T loadContact(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rContactParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"contacts\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getContactsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique contacts row by GUID (" + Statement.convertObjectToString(this.getContactsGuid(), null) + ")!");
			rContactParent = lst.get(0);
		}
		return (T) rContactParent;
	}
	
	protected Object rTimeSheetParent = null;
	public <T extends TimeSheetsRow> T loadTimeSheet(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rTimeSheetParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"time sheets\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getTimeSheetsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique time sheets row by GUID (" + Statement.convertObjectToString(this.getTimeSheetsGuid(), null) + ")!");
			rTimeSheetParent = lst.get(0);
		}
		return (T) rTimeSheetParent;
	}
	
	protected Object rDocumentParent = null;
	public <T extends DocumentsRow> T loadDocument(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rDocumentParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"documents\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getDocumentsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique documents row by GUID (" + Statement.convertObjectToString(this.getDocumentsGuid(), null) + ")!");
			rDocumentParent = lst.get(0);
		}
		return (T) rDocumentParent;
	}
	
	protected Object rJobParent = null;
	public <T extends JobsRow> T loadJob(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rJobParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"jobs\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getJobsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique jobs row by GUID (" + Statement.convertObjectToString(this.getJobsGuid(), null) + ")!");
			rJobParent = lst.get(0);
		}
		return (T) rJobParent;
	}
	
	protected Object rDepartmentParent = null;
	public <T extends DepartmentsRow> T loadDepartment(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rDepartmentParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"departments\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getDepartmentsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique departments row by GUID (" + Statement.convertObjectToString(this.getDepartmentsGuid(), null) + ")!");
			rDepartmentParent = lst.get(0);
		}
		return (T) rDepartmentParent;
	}
	

	// unique key loaders
	
	public static <T extends TimeEntriesRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"time entries\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique time entries row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
}
