package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface TimeEntriesInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setEntryTypesGuid(java.lang.String value) throws Exception;
	java.lang.String getEntryTypesGuid();
	
	boolean setEmployeesGuid(java.lang.String value) throws Exception;
	java.lang.String getEmployeesGuid();
	
	boolean setContactsGuid(java.lang.String value) throws Exception;
	java.lang.String getContactsGuid();
	
	boolean setTimeSheetsGuid(java.lang.String value) throws Exception;
	java.lang.String getTimeSheetsGuid();
	
	boolean setDocumentsGuid(java.lang.String value) throws Exception;
	java.lang.String getDocumentsGuid();
	
	boolean setJobsGuid(java.lang.String value) throws Exception;
	java.lang.String getJobsGuid();
	
	boolean setDepartmentsGuid(java.lang.String value) throws Exception;
	java.lang.String getDepartmentsGuid();
	
	boolean setStarted(java.util.Date value) throws Exception;
	java.util.Date getStarted();
	
	boolean setEnded(java.util.Date value) throws Exception;
	java.util.Date getEnded();
	
	boolean setDescription(java.lang.String value) throws Exception;
	java.lang.String getDescription();
	
	
	
	<T extends TimeEntryTypesRow> T loadTimeEntryType(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends EmployeesRow> T loadEmployee(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends ContactsRow> T loadContact(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends TimeSheetsRow> T loadTimeSheet(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends DocumentsRow> T loadDocument(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends JobsRow> T loadJob(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends DepartmentsRow> T loadDepartment(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
