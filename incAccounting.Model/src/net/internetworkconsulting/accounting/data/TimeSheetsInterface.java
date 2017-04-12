package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface TimeSheetsInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setUsersGuid(java.lang.String value) throws Exception;
	java.lang.String getUsersGuid();
	
	boolean setEmployeesGuid(java.lang.String value) throws Exception;
	java.lang.String getEmployeesGuid();
	
	boolean setPayrollChecksGuid(java.lang.String value) throws Exception;
	java.lang.String getPayrollChecksGuid();
	
	
	<T extends TimeEntriesRow> List<T> loadTimeEntries(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	
	<T extends UsersRow> T loadUser(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends ContactsRow> T loadEmployee(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends PayrollChecksRow> T loadPayrollCheck(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
