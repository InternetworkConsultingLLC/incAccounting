package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface TimeSheetsInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setEmployeesGuid(java.lang.String value) throws Exception;
	java.lang.String getEmployeesGuid();
	
	boolean setNumber(java.lang.String value) throws Exception;
	java.lang.String getNumber();
	
	boolean setPeriodEnding(java.sql.Date value) throws Exception;
	java.sql.Date getPeriodEnding();
	
	boolean setTotalHours(java.math.BigDecimal value) throws Exception;
	java.math.BigDecimal getTotalHours();
	
	boolean setPeriodStarting(java.sql.Date value) throws Exception;
	java.sql.Date getPeriodStarting();
	
	
	<T extends TimeEntriesRow> List<T> loadTimeEntries(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	
	<T extends ContactsRow> T loadEmployee(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
