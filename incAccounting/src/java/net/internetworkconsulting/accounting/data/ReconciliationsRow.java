package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class ReconciliationsRow extends Row implements ReconciliationsInterface {
	public ReconciliationsRow() { 
		super(); 
		setSqlTableName("reconciliations");
		setSqlSecurableGuid("d0b19a18049abe7044966fc531b72319");
	}
	public static String TABLE_NAME = "reconciliations";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String ACCOUNTS_GUID = "Accounts GUID";
	public boolean setAccountsGuid(java.lang.String value) throws Exception { return set(ACCOUNTS_GUID, value); }
	public java.lang.String getAccountsGuid() { return (java.lang.String) get(ACCOUNTS_GUID); }
	
	public static String DATE = "Date";
	public boolean setDate(java.util.Date value) throws Exception { return set(DATE, value); }
	public java.util.Date getDate() { return (java.util.Date) get(DATE); }
	
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
			Statement stmt = new Statement("SELECT * FROM \"transaction lines\" WHERE \"Reconciliations GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstTransactionLinesChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstTransactionLinesChildren;
	}
	

	// parent loaders
	
	protected Object rAccountParent = null;
	public <T extends AccountsRow> T loadAccount(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rAccountParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"accounts\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getAccountsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique accounts row by GUID (" + Statement.convertObjectToString(this.getAccountsGuid(), null) + ")!");
			rAccountParent = lst.get(0);
		}
		return (T) rAccountParent;
	}
	

	// unique key loaders
	
	public static <T extends ReconciliationsRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"reconciliations\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique reconciliations row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
}
