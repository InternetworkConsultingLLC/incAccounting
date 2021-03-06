package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class EmployeesRow extends Row implements EmployeesInterface {
	public EmployeesRow() { 
		super(); 
		setSqlTableName("employees");
		setSqlSecurableGuid("582ca3f7cbaf4edcc1b445f8ea90b503");
	}
	public static String TABLE_NAME = "employees";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String FIRST_NAME = "First Name";
	public boolean setFirstName(java.lang.String value) throws Exception { return set(FIRST_NAME, value); }
	public java.lang.String getFirstName() { return (java.lang.String) get(FIRST_NAME); }
	
	public static String LAST_NAME = "Last Name";
	public boolean setLastName(java.lang.String value) throws Exception { return set(LAST_NAME, value); }
	public java.lang.String getLastName() { return (java.lang.String) get(LAST_NAME); }
	
	public static String SUFFIX = "Suffix";
	public boolean setSuffix(java.lang.String value) throws Exception { return set(SUFFIX, value); }
	public java.lang.String getSuffix() { return (java.lang.String) get(SUFFIX); }
	
	public static String TAX_ID = "Tax ID";
	public boolean setTaxId(java.lang.String value) throws Exception { return set(TAX_ID, value); }
	public java.lang.String getTaxId() { return (java.lang.String) get(TAX_ID); }
	
	public static String DATE_OF_BIRTH = "Date of Birth";
	public boolean setDateOfBirth(java.util.Date value) throws Exception { return set(DATE_OF_BIRTH, value); }
	public java.util.Date getDateOfBirth() { return (java.util.Date) get(DATE_OF_BIRTH); }
	
	public static String IS_MALE = "Is Male";
	public boolean setIsMale(java.lang.Boolean value) throws Exception { return set(IS_MALE, value); }
	public java.lang.Boolean getIsMale() { return (java.lang.Boolean) get(IS_MALE); }
	
	public static String ETHNICITY = "Ethnicity";
	public boolean setEthnicity(java.lang.String value) throws Exception { return set(ETHNICITY, value); }
	public java.lang.String getEthnicity() { return (java.lang.String) get(ETHNICITY); }
	
	public static String JOB_TITLE = "Job Title";
	public boolean setJobTitle(java.lang.String value) throws Exception { return set(JOB_TITLE, value); }
	public java.lang.String getJobTitle() { return (java.lang.String) get(JOB_TITLE); }
	
	public static String SUPERVISOR_CONTACTS_GUID = "Supervisor Contacts GUID";
	public boolean setSupervisorContactsGuid(java.lang.String value) throws Exception { return set(SUPERVISOR_CONTACTS_GUID, value); }
	public java.lang.String getSupervisorContactsGuid() { return (java.lang.String) get(SUPERVISOR_CONTACTS_GUID); }
	
	public static String DATE_HIRRED = "Date Hirred";
	public boolean setDateHirred(java.util.Date value) throws Exception { return set(DATE_HIRRED, value); }
	public java.util.Date getDateHirred() { return (java.util.Date) get(DATE_HIRRED); }
	
	public static String DATE_TERMINATED = "Date Terminated";
	public boolean setDateTerminated(java.util.Date value) throws Exception { return set(DATE_TERMINATED, value); }
	public java.util.Date getDateTerminated() { return (java.util.Date) get(DATE_TERMINATED); }
	
	public static String DATE_VERIFIED = "Date Verified";
	public boolean setDateVerified(java.util.Date value) throws Exception { return set(DATE_VERIFIED, value); }
	public java.util.Date getDateVerified() { return (java.util.Date) get(DATE_VERIFIED); }
	
	public static String INSURANCE = "Insurance";
	public boolean setInsurance(java.lang.Boolean value) throws Exception { return set(INSURANCE, value); }
	public java.lang.Boolean getInsurance() { return (java.lang.Boolean) get(INSURANCE); }
	
	public static String VACATION = "Vacation";
	public boolean setVacation(java.lang.Boolean value) throws Exception { return set(VACATION, value); }
	public java.lang.Boolean getVacation() { return (java.lang.Boolean) get(VACATION); }
	

	// child loaders
	
	protected Object lstPayrollChecksChildren = null;
	public <T extends PayrollChecksRow> List<T> loadPayrollChecks(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstPayrollChecksChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"payroll checks\" WHERE \"Employees GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstPayrollChecksChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstPayrollChecksChildren;
	}
	
	protected Object lstTimeEntriesChildren = null;
	public <T extends TimeEntriesRow> List<T> loadTimeEntries(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstTimeEntriesChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"time entries\" WHERE \"Employees GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstTimeEntriesChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstTimeEntriesChildren;
	}
	
	protected Object lstUsersChildren = null;
	public <T extends UsersRow> List<T> loadUsers(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstUsersChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"users\" WHERE \"Employees GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstUsersChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstUsersChildren;
	}
	

	// parent loaders
	
	protected Object rContactParent = null;
	public <T extends ContactsRow> T loadContact(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rContactParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"contacts\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique contacts row by GUID (" + Statement.convertObjectToString(this.getGuid(), null) + ")!");
			rContactParent = lst.get(0);
		}
		return (T) rContactParent;
	}
	
	protected Object rSupervisorParent = null;
	public <T extends ContactsRow> T loadSupervisor(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rSupervisorParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"contacts\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getSupervisorContactsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique contacts row by GUID (" + Statement.convertObjectToString(this.getSupervisorContactsGuid(), null) + ")!");
			rSupervisorParent = lst.get(0);
		}
		return (T) rSupervisorParent;
	}
	

	// unique key loaders
	
	public static <T extends EmployeesRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"employees\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique employees row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
}
