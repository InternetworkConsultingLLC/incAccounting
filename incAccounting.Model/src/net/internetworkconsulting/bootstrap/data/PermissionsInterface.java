package net.internetworkconsulting.bootstrap.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface PermissionsInterface {
	
	boolean setGroupsGuid(java.lang.String value) throws Exception;
	java.lang.String getGroupsGuid();
	
	boolean setSecurablesGuid(java.lang.String value) throws Exception;
	java.lang.String getSecurablesGuid();
	
	boolean setCanCreate(java.lang.Boolean value) throws Exception;
	java.lang.Boolean getCanCreate();
	
	boolean setCanRead(java.lang.Boolean value) throws Exception;
	java.lang.Boolean getCanRead();
	
	boolean setCanUpdate(java.lang.Boolean value) throws Exception;
	java.lang.Boolean getCanUpdate();
	
	boolean setCanDelete(java.lang.Boolean value) throws Exception;
	java.lang.Boolean getCanDelete();
	
	
	
	<T extends GroupsRow> T loadGroup(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends SecurablesRow> T loadSecurable(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
