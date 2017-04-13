package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface TimeEntryTypesInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setDescription(java.lang.String value) throws Exception;
	java.lang.String getDescription();
	
	boolean setPayable(java.lang.Boolean value) throws Exception;
	java.lang.Boolean getPayable();
	
	
	<T extends TimeEntriesRow> List<T> loadTimeEntries(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	
}
