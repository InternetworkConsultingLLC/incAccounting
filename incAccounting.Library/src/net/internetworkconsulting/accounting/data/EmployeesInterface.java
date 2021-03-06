package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface EmployeesInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setFirstName(java.lang.String value) throws Exception;
	java.lang.String getFirstName();
	
	boolean setLastName(java.lang.String value) throws Exception;
	java.lang.String getLastName();
	
	boolean setSuffix(java.lang.String value) throws Exception;
	java.lang.String getSuffix();
	
	boolean setTaxId(java.lang.String value) throws Exception;
	java.lang.String getTaxId();
	
	boolean setDateOfBirth(java.util.Date value) throws Exception;
	java.util.Date getDateOfBirth();
	
	boolean setIsMale(java.lang.Boolean value) throws Exception;
	java.lang.Boolean getIsMale();
	
	boolean setEthnicity(java.lang.String value) throws Exception;
	java.lang.String getEthnicity();
	
	boolean setJobTitle(java.lang.String value) throws Exception;
	java.lang.String getJobTitle();
	
	boolean setSupervisorContactsGuid(java.lang.String value) throws Exception;
	java.lang.String getSupervisorContactsGuid();
	
	boolean setDateHirred(java.util.Date value) throws Exception;
	java.util.Date getDateHirred();
	
	boolean setDateTerminated(java.util.Date value) throws Exception;
	java.util.Date getDateTerminated();
	
	boolean setDateVerified(java.util.Date value) throws Exception;
	java.util.Date getDateVerified();
	
	boolean setInsurance(java.lang.Boolean value) throws Exception;
	java.lang.Boolean getInsurance();
	
	boolean setVacation(java.lang.Boolean value) throws Exception;
	java.lang.Boolean getVacation();
	
	
	<T extends PayrollChecksRow> List<T> loadPayrollChecks(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends TimeEntriesRow> List<T> loadTimeEntries(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends UsersRow> List<T> loadUsers(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	
	<T extends ContactsRow> T loadContact(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends ContactsRow> T loadSupervisor(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
