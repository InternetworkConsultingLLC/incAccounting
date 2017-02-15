/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import net.internetworkconsulting.data.mysql.Statement;
import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;

public class TransactionTypesRow extends Row implements TransactionTypesInterface {
	public TransactionTypesRow() { 
		super(); 
		setSqlTableName("transaction types");
		setSqlSecurableGuid("98c6172a86db962e97ddbc6faa5aaab2");
	}
	public static String TABLE_NAME = "transaction types";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String NAME = "Name";
	public boolean setName(java.lang.String value) throws Exception { return set(NAME, value); }
	public java.lang.String getName() { return (java.lang.String) get(NAME); }
	
	public static String EDIT_URL = "Edit URL";
	public boolean setEditUrl(java.lang.String value) throws Exception { return set(EDIT_URL, value); }
	public java.lang.String getEditUrl() { return (java.lang.String) get(EDIT_URL); }
	
	public static String LIST_URL = "List URL";
	public boolean setListUrl(java.lang.String value) throws Exception { return set(LIST_URL, value); }
	public java.lang.String getListUrl() { return (java.lang.String) get(LIST_URL); }
	
	public static String IS_ALLOWED = "Is Allowed";
	public boolean setIsAllowed(java.lang.Boolean value) throws Exception { return set(IS_ALLOWED, value); }
	public java.lang.Boolean getIsAllowed() { return (java.lang.Boolean) get(IS_ALLOWED); }
	

	// child loaders
	
	protected Object lstDocumentTypeChildren = null;
	public <T extends DocumentTypesRow> List<T> loadDocumentType(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstDocumentTypeChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"document types\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstDocumentTypeChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstDocumentTypeChildren;
	}
	
	protected Object lstPaymentTypeChildren = null;
	public <T extends PaymentTypesRow> List<T> loadPaymentType(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstPaymentTypeChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"payment types\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstPaymentTypeChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstPaymentTypeChildren;
	}
	
	protected Object lstTransactionsChildren = null;
	public <T extends TransactionsRow> List<T> loadTransactions(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstTransactionsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"transactions\" WHERE \"Transaction Types GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstTransactionsChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstTransactionsChildren;
	}
	

	// parent loaders
	

	// unique key loaders
	
	public static <T extends TransactionTypesRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"transaction types\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique transaction types row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
	public static <T extends TransactionTypesRow> T loadByName(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"transaction types\" WHERE \"Name\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique transaction types row by 'Name': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	

	// load all
	public static <T extends TransactionTypesRow> List<T> loadAll(AdapterInterface adapter, Class model) throws Exception {
		Statement stmt = new Statement("SELECT * FROM \"transactions\"");
		return (List<T>) adapter.load(model, stmt);
	}
}
