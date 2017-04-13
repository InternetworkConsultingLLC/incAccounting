package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface SettingsInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setUsersGuid(java.lang.String value) throws Exception;
	java.lang.String getUsersGuid();
	
	boolean setKey(java.lang.String value) throws Exception;
	java.lang.String getKey();
	
	boolean setType(java.lang.String value) throws Exception;
	java.lang.String getType();
	
	boolean setOptionQuery(java.lang.String value) throws Exception;
	java.lang.String getOptionQuery();
	
	boolean setValue(java.lang.String value) throws Exception;
	java.lang.String getValue();
	
	
	
	<T extends UsersRow> T loadUser(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
