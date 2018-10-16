package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface ContactNotesInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setContactsGuid(java.lang.String value) throws Exception;
	java.lang.String getContactsGuid();
	
	boolean setUsersGuid(java.lang.String value) throws Exception;
	java.lang.String getUsersGuid();
	
	boolean setDated(java.util.Date value) throws Exception;
	java.util.Date getDated();
	
	boolean setSubject(java.lang.String value) throws Exception;
	java.lang.String getSubject();
	
	boolean setMemorandum(java.lang.String value) throws Exception;
	java.lang.String getMemorandum();
	
	
	
	<T extends ContactsRow> T loadContact(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
