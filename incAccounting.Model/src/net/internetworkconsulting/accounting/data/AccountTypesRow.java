/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class AccountTypesRow extends Row implements AccountTypesInterface {
	public AccountTypesRow() { 
		super(); 
		setSqlTableName("Account Types");
		setSqlSecurableGuid("720cdbbebae7124d2c3fdc1b46664655");
	}
	public static String TABLE_NAME = "Account Types";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String NAME = "Name";
	public boolean setName(java.lang.String value) throws Exception { return set(NAME, value); }
	public java.lang.String getName() { return (java.lang.String) get(NAME); }
	
	public static String GETS_CLOSED = "Gets Closed";
	public boolean setGetsClosed(java.lang.Boolean value) throws Exception { return set(GETS_CLOSED, value); }
	public java.lang.Boolean getGetsClosed() { return (java.lang.Boolean) get(GETS_CLOSED); }
	

	// child loaders
	
	protected Object lstAccountsChildren = null;
	public <T extends AccountsRow> List<T> loadAccounts(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstAccountsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Accounts\" WHERE \"Account Types GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstAccountsChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstAccountsChildren;
	}
	

	// parent loaders
	

	// unique key loaders
	
	public static <T extends AccountTypesRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"Account Types\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique Account Types row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	

	// load all
	public static <T extends AccountTypesRow> List<T> loadAll(AdapterInterface adapter, Class model) throws Exception {
		Statement stmt = new Statement("SELECT * FROM \"Accounts\"");
		return (List<T>) adapter.load(model, stmt, true);
	}
}
