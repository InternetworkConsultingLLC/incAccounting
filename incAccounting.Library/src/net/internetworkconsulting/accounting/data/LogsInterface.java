package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface LogsInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setOccured(java.util.Date value) throws Exception;
	java.util.Date getOccured();
	
	boolean setUsersGuid(java.lang.String value) throws Exception;
	java.lang.String getUsersGuid();
	
	boolean setCodeGuid(java.lang.String value) throws Exception;
	java.lang.String getCodeGuid();
	
	boolean setLog(java.lang.String value) throws Exception;
	java.lang.String getLog();
	
	boolean setDetails(java.lang.String value) throws Exception;
	java.lang.String getDetails();
	
	
	
	<T extends UsersRow> T loadUser(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
