package net.internetworkconsulting.bootstrap.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface GroupsInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setIsAllowed(java.lang.Boolean value) throws Exception;
	java.lang.Boolean getIsAllowed();
	
	boolean setDisplayName(java.lang.String value) throws Exception;
	java.lang.String getDisplayName();
	
	boolean setEmailAddress(java.lang.String value) throws Exception;
	java.lang.String getEmailAddress();
	
	
	<T extends MembershipsRow> List<T> loadMemberships(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends PermissionsRow> List<T> loadPermissions(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	
}
