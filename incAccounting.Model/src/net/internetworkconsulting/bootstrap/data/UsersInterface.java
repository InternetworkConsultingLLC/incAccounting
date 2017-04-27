package net.internetworkconsulting.bootstrap.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface UsersInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setIsAllowed(java.lang.Boolean value) throws Exception;
	java.lang.Boolean getIsAllowed();
	
	boolean setDisplayName(java.lang.String value) throws Exception;
	java.lang.String getDisplayName();
	
	boolean setSqlUser(java.lang.String value) throws Exception;
	java.lang.String getSqlUser();
	
	boolean setEmailAddress(java.lang.String value) throws Exception;
	java.lang.String getEmailAddress();
	
	boolean setPasswordDate(java.sql.Date value) throws Exception;
	java.sql.Date getPasswordDate();
	
	boolean setAddComputer(java.lang.Boolean value) throws Exception;
	java.lang.Boolean getAddComputer();
	
	
	<T extends LogsRow> List<T> loadLogs(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends MembershipsRow> List<T> loadMemberships(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends SettingsRow> List<T> loadSettings(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	
}
