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
		setSqlTableName("account types");
		setSqlSecurableGuid("5c75729edb072ea2f50ff93259ee2813");
	}
	public static String TABLE_NAME = "account types";

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
			Statement stmt = new Statement("SELECT * FROM \"accounts\" WHERE \"Account Types GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstAccountsChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstAccountsChildren;
	}
	

	// parent loaders
	

	// unique key loaders
	
	public static <T extends AccountTypesRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"account types\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique account types row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
	public static <T extends AccountTypesRow> T loadByName(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"account types\" WHERE \"Name\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique account types row by 'Name': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
}
