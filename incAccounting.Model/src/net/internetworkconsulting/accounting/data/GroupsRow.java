/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class GroupsRow extends Row implements GroupsInterface {
	public GroupsRow() { 
		super(); 
		setSqlTableName("Groups");
		setSqlSecurableGuid("a37ede293936e29279ed543129451ec3");
	}
	public static String TABLE_NAME = "Groups";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String IS_ALLOWED = "Is Allowed";
	public boolean setIsAllowed(java.lang.Boolean value) throws Exception { return set(IS_ALLOWED, value); }
	public java.lang.Boolean getIsAllowed() { return (java.lang.Boolean) get(IS_ALLOWED); }
	
	public static String DISPLAY_NAME = "Display Name";
	public boolean setDisplayName(java.lang.String value) throws Exception { return set(DISPLAY_NAME, value); }
	public java.lang.String getDisplayName() { return (java.lang.String) get(DISPLAY_NAME); }
	
	public static String EMAIL_ADDRESS = "Email Address";
	public boolean setEmailAddress(java.lang.String value) throws Exception { return set(EMAIL_ADDRESS, value); }
	public java.lang.String getEmailAddress() { return (java.lang.String) get(EMAIL_ADDRESS); }
	

	// child loaders
	
	protected Object lstMembershipsChildren = null;
	public <T extends MembershipsRow> List<T> loadMemberships(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstMembershipsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Memberships\" WHERE \"Groups GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstMembershipsChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstMembershipsChildren;
	}
	
	protected Object lstPermissionsChildren = null;
	public <T extends PermissionsRow> List<T> loadPermissions(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstPermissionsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Permissions\" WHERE \"Groups GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstPermissionsChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstPermissionsChildren;
	}
	

	// parent loaders
	

	// unique key loaders
	
	public static <T extends GroupsRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"Groups\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique Groups row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	

	// load all
	public static <T extends GroupsRow> List<T> loadAll(AdapterInterface adapter, Class model) throws Exception {
		Statement stmt = new Statement("SELECT * FROM \"Permissions\"");
		return (List<T>) adapter.load(model, stmt, true);
	}
}
