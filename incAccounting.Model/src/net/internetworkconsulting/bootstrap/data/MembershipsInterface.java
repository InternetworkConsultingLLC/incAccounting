/*
 * incGenerator - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.bootstrap.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface MembershipsInterface {
	
	boolean setUsersGuid(java.lang.String value) throws Exception;
	java.lang.String getUsersGuid();
	
	boolean setGroupsGuid(java.lang.String value) throws Exception;
	java.lang.String getGroupsGuid();
	
	
	
	<T extends UsersRow> T loadUser(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends GroupsRow> T loadGroup(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
