/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class ReconciliationsRow extends Row implements ReconciliationsInterface {
	public ReconciliationsRow() { 
		super(); 
		setSqlTableName("Reconciliations");
		setSqlSecurableGuid("4a8a1dc4a61260a1d51e3b9f8bb5f18f");
	}
	public static String TABLE_NAME = "Reconciliations";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String ACCOUNTS_GUID = "Accounts GUID";
	public boolean setAccountsGuid(java.lang.String value) throws Exception { return set(ACCOUNTS_GUID, value); }
	public java.lang.String getAccountsGuid() { return (java.lang.String) get(ACCOUNTS_GUID); }
	
	public static String DATE = "Date";
	public boolean setDate(java.sql.Date value) throws Exception { return set(DATE, value); }
	public java.sql.Date getDate() { return (java.sql.Date) get(DATE); }
	
	public static String STATEMENT_ENDING_BALANCE = "Statement Ending Balance";
	public boolean setStatementEndingBalance(java.math.BigDecimal value) throws Exception { return set(STATEMENT_ENDING_BALANCE, value); }
	public java.math.BigDecimal getStatementEndingBalance() { return (java.math.BigDecimal) get(STATEMENT_ENDING_BALANCE); }
	
	public static String OFF_BY = "Off By";
	public boolean setOffBy(java.math.BigDecimal value) throws Exception { return set(OFF_BY, value); }
	public java.math.BigDecimal getOffBy() { return (java.math.BigDecimal) get(OFF_BY); }
	

	// child loaders
	
	protected Object lstTransactionLinesChildren = null;
	public <T extends TransactionLinesRow> List<T> loadTransactionLines(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstTransactionLinesChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Transaction Lines\" WHERE \"Reconciliations GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstTransactionLinesChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstTransactionLinesChildren;
	}
	

	// parent loaders
	
	protected Object rAccountParent = null;
	public <T extends AccountsRow> T loadAccount(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rAccountParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Accounts\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getAccountsGuid());
			List<T> lst = adapter.load(model, stmt);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique Accounts row by GUID (" + Statement.convertObjectToString(this.getAccountsGuid(), null) + ")!");
			rAccountParent = lst.get(0);
		}
		return (T) rAccountParent;
	}
	

	// unique key loaders
	
	public static <T extends ReconciliationsRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"Reconciliations\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique Reconciliations row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	

	// load all
	public static <T extends ReconciliationsRow> List<T> loadAll(AdapterInterface adapter, Class model) throws Exception {
		Statement stmt = new Statement("SELECT * FROM \"Reconciliations\"");
		return (List<T>) adapter.load(model, stmt);
	}
}
