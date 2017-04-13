/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class RegisterEntriesRow extends Row implements RegisterEntriesInterface {
	public RegisterEntriesRow() { 
		super(); 
		setSqlTableName("Register Entries");
		setSqlSecurableGuid("84e3a6b6fa39c6856bca9ef58c28a03e");
	}
	public static String TABLE_NAME = "Register Entries";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String CONTACTS_GUID = "Contacts GUID";
	public boolean setContactsGuid(java.lang.String value) throws Exception { return set(CONTACTS_GUID, value); }
	public java.lang.String getContactsGuid() { return (java.lang.String) get(CONTACTS_GUID); }
	

	// child loaders
	

	// parent loaders
	
	protected Object rTransactionLineParent = null;
	public <T extends TransactionLinesRow> T loadTransactionLine(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rTransactionLineParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Transaction Lines\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique Transaction Lines row by GUID (" + Statement.convertObjectToString(this.getGuid(), null) + ")!");
			rTransactionLineParent = lst.get(0);
		}
		return (T) rTransactionLineParent;
	}
	
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
	
	public static <T extends RegisterEntriesRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"Register Entries\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique Register Entries row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	

	// load all
	public static <T extends RegisterEntriesRow> List<T> loadAll(AdapterInterface adapter, Class model) throws Exception {
		Statement stmt = new Statement("SELECT * FROM \"Register Entries\"");
		return (List<T>) adapter.load(model, stmt, true);
	}
}
