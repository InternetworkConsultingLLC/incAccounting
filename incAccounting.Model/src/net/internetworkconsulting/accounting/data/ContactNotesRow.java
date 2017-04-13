/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class ContactNotesRow extends Row implements ContactNotesInterface {
	public ContactNotesRow() { 
		super(); 
		setSqlTableName("Contact Notes");
		setSqlSecurableGuid("74296745c146fc4ffc4afda0f19f1f2c");
	}
	public static String TABLE_NAME = "Contact Notes";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String CONTACTS_GUID = "Contacts GUID";
	public boolean setContactsGuid(java.lang.String value) throws Exception { return set(CONTACTS_GUID, value); }
	public java.lang.String getContactsGuid() { return (java.lang.String) get(CONTACTS_GUID); }
	
	public static String USERS_GUID = "Users GUID";
	public boolean setUsersGuid(java.lang.String value) throws Exception { return set(USERS_GUID, value); }
	public java.lang.String getUsersGuid() { return (java.lang.String) get(USERS_GUID); }
	
	public static String DATED = "Dated";
	public boolean setDated(java.sql.Timestamp value) throws Exception { return set(DATED, value); }
	public java.sql.Timestamp getDated() { return (java.sql.Timestamp) get(DATED); }
	
	public static String SUBJECT = "Subject";
	public boolean setSubject(java.lang.String value) throws Exception { return set(SUBJECT, value); }
	public java.lang.String getSubject() { return (java.lang.String) get(SUBJECT); }
	
	public static String MEMORANDUM = "Memorandum";
	public boolean setMemorandum(java.lang.String value) throws Exception { return set(MEMORANDUM, value); }
	public java.lang.String getMemorandum() { return (java.lang.String) get(MEMORANDUM); }
	

	// child loaders
	

	// parent loaders
	
	protected Object rContactParent = null;
	public <T extends ContactsRow> T loadContact(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rContactParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Contacts\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getContactsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique Contacts row by GUID (" + Statement.convertObjectToString(this.getContactsGuid(), null) + ")!");
			rContactParent = lst.get(0);
		}
		return (T) rContactParent;
	}
	

	// unique key loaders
	
	public static <T extends ContactNotesRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"Contact Notes\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique Contact Notes row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	

	// load all
	public static <T extends ContactNotesRow> List<T> loadAll(AdapterInterface adapter, Class model) throws Exception {
		Statement stmt = new Statement("SELECT * FROM \"Contact Notes\"");
		return (List<T>) adapter.load(model, stmt, true);
	}
}
