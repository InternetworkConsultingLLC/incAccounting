/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class MembershipsRow extends Row implements MembershipsInterface {
	public MembershipsRow() { 
		super(); 
		setSqlTableName("memberships");
		setSqlSecurableGuid("f060aa0e3d493f5fd489cf45446706bc");
	}
	public static String TABLE_NAME = "memberships";

	// columns
	
	public static String USERS_GUID = "Users GUID";
	public boolean setUsersGuid(java.lang.String value) throws Exception { return set(USERS_GUID, value); }
	public java.lang.String getUsersGuid() { return (java.lang.String) get(USERS_GUID); }
	
	public static String GROUPS_GUID = "Groups GUID";
	public boolean setGroupsGuid(java.lang.String value) throws Exception { return set(GROUPS_GUID, value); }
	public java.lang.String getGroupsGuid() { return (java.lang.String) get(GROUPS_GUID); }
	

	// child loaders
	

	// parent loaders
	
	protected Object rUserParent = null;
	public <T extends UsersRow> T loadUser(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rUserParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"users\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getUsersGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique users row by GUID (" + Statement.convertObjectToString(this.getUsersGuid(), null) + ")!");
			rUserParent = lst.get(0);
		}
		return (T) rUserParent;
	}
	
	protected Object rGroupParent = null;
	public <T extends GroupsRow> T loadGroup(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rGroupParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"groups\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGroupsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique groups row by GUID (" + Statement.convertObjectToString(this.getGroupsGuid(), null) + ")!");
			rGroupParent = lst.get(0);
		}
		return (T) rGroupParent;
	}
	

	// unique key loaders
	
}
