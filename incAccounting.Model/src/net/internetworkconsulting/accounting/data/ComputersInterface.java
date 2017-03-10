package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface ComputersInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setDescription(java.lang.String value) throws Exception;
	java.lang.String getDescription();
	
	boolean setMacAddress(java.lang.String value) throws Exception;
	java.lang.String getMacAddress();
	
	boolean setIsAllowed(java.lang.Boolean value) throws Exception;
	java.lang.Boolean getIsAllowed();
	
	
	<T extends LogsRow> List<T> loadLogs(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	
}
