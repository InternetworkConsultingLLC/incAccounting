/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import net.internetworkconsulting.data.mysql.Statement;
import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;

public class ContactTypesRow extends Row implements ContactTypesInterface {
	public ContactTypesRow() { 
		super(); 
		setSqlTableName("contact types");
		setSqlSecurableGuid("7c61fdef99c1454ce4e7f3defb9ce094");
	}
	public static String TABLE_NAME = "contact types";

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
	

	// child loaders
	
	protected Object lstContactsChildren = null;
	public <T extends ContactsRow> List<T> loadContacts(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstContactsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"contacts\" WHERE \"Contact Types GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstContactsChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstContactsChildren;
	}
	

	// parent loaders
	

	// unique key loaders
	
	public static <T extends ContactTypesRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"contact types\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique contact types row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
	public static <T extends ContactTypesRow> T loadByDisplayName(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"contact types\" WHERE \"Display Name\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique contact types row by 'Display Name': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	

	// load all
	public static <T extends ContactTypesRow> List<T> loadAll(AdapterInterface adapter, Class model) throws Exception {
		Statement stmt = new Statement("SELECT * FROM \"contacts\"");
		return (List<T>) adapter.load(model, stmt);
	}
}
