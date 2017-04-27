/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.bootstrap.data;

import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class PermissionsRow extends Row implements PermissionsInterface {
	public PermissionsRow() { 
		super(); 
		setSqlTableName("Permissions");
		setSqlSecurableGuid("d08ccf52b4cdd08e41cfb99ec42e0b29");
	}
	public static String TABLE_NAME = "Permissions";

	// columns
	
	public static String GROUPS_GUID = "Groups GUID";
	public boolean setGroupsGuid(java.lang.String value) throws Exception { return set(GROUPS_GUID, value); }
	public java.lang.String getGroupsGuid() { return (java.lang.String) get(GROUPS_GUID); }
	
	public static String SECURABLES_GUID = "Securables GUID";
	public boolean setSecurablesGuid(java.lang.String value) throws Exception { return set(SECURABLES_GUID, value); }
	public java.lang.String getSecurablesGuid() { return (java.lang.String) get(SECURABLES_GUID); }
	
	public static String CAN_CREATE = "Can Create";
	public boolean setCanCreate(java.lang.Boolean value) throws Exception { return set(CAN_CREATE, value); }
	public java.lang.Boolean getCanCreate() { return (java.lang.Boolean) get(CAN_CREATE); }
	
	public static String CAN_READ = "Can Read";
	public boolean setCanRead(java.lang.Boolean value) throws Exception { return set(CAN_READ, value); }
	public java.lang.Boolean getCanRead() { return (java.lang.Boolean) get(CAN_READ); }
	
	public static String CAN_UPDATE = "Can Update";
	public boolean setCanUpdate(java.lang.Boolean value) throws Exception { return set(CAN_UPDATE, value); }
	public java.lang.Boolean getCanUpdate() { return (java.lang.Boolean) get(CAN_UPDATE); }
	
	public static String CAN_DELETE = "Can Delete";
	public boolean setCanDelete(java.lang.Boolean value) throws Exception { return set(CAN_DELETE, value); }
	public java.lang.Boolean getCanDelete() { return (java.lang.Boolean) get(CAN_DELETE); }
	

	// child loaders
	

	// parent loaders
	
	protected Object rGroupParent = null;
	public <T extends GroupsRow> T loadGroup(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rGroupParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Groups\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGroupsGuid());
			List<T> lst = adapter.load(model, stmt);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique Groups row by GUID (" + Statement.convertObjectToString(this.getGroupsGuid(), null) + ")!");
			rGroupParent = lst.get(0);
		}
		return (T) rGroupParent;
	}
	
	protected Object rSecurableParent = null;
	public <T extends SecurablesRow> T loadSecurable(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rSecurableParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Securables\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getSecurablesGuid());
			List<T> lst = adapter.load(model, stmt);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique Securables row by GUID (" + Statement.convertObjectToString(this.getSecurablesGuid(), null) + ")!");
			rSecurableParent = lst.get(0);
		}
		return (T) rSecurableParent;
	}
	

	// unique key loaders
	

	// load all
	public static <T extends PermissionsRow> List<T> loadAll(AdapterInterface adapter, Class model) throws Exception {
		Statement stmt = new Statement("SELECT * FROM \"Permissions\"");
		return (List<T>) adapter.load(model, stmt);
	}
}
