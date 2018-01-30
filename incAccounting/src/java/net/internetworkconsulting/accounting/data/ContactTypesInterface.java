package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface ContactTypesInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setIsAllowed(java.lang.Boolean value) throws Exception;
	java.lang.Boolean getIsAllowed();
	
	boolean setDisplayName(java.lang.String value) throws Exception;
	java.lang.String getDisplayName();
	
	
	<T extends ContactsRow> List<T> loadContacts(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	
}
