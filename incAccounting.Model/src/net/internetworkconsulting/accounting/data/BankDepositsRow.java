/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class BankDepositsRow extends Row implements BankDepositsInterface {
	public BankDepositsRow() { 
		super(); 
		setSqlTableName("bank deposits");
		setSqlSecurableGuid("f7721251e52127b9e67e464aee80eedb");
	}
	public static String TABLE_NAME = "bank deposits";

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
	
	public static String NUMBER = "Number";
	public boolean setNumber(java.lang.String value) throws Exception { return set(NUMBER, value); }
	public java.lang.String getNumber() { return (java.lang.String) get(NUMBER); }
	
	public static String ITEMS = "Items";
	public boolean setItems(java.lang.Integer value) throws Exception { return set(ITEMS, value); }
	public java.lang.Integer getItems() { return (java.lang.Integer) get(ITEMS); }
	
	public static String TOTAL = "Total";
	public boolean setTotal(java.math.BigDecimal value) throws Exception { return set(TOTAL, value); }
	public java.math.BigDecimal getTotal() { return (java.math.BigDecimal) get(TOTAL); }
	
	public static String POSTED_TRANSACTIONS_GUID = "Posted Transactions GUID";
	public boolean setPostedTransactionsGuid(java.lang.String value) throws Exception { return set(POSTED_TRANSACTIONS_GUID, value); }
	public java.lang.String getPostedTransactionsGuid() { return (java.lang.String) get(POSTED_TRANSACTIONS_GUID); }
	

	// child loaders
	
	protected Object lstPaymentsChildren = null;
	public <T extends PaymentsRow> List<T> loadPayments(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstPaymentsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"payments\" WHERE \"Bank Deposits GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstPaymentsChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstPaymentsChildren;
	}
	

	// parent loaders
	
	protected Object rTransactionParent = null;
	public <T extends TransactionsRow> T loadTransaction(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rTransactionParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"transactions\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getPostedTransactionsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique transactions row by GUID (" + Statement.convertObjectToString(this.getPostedTransactionsGuid(), null) + ")!");
			rTransactionParent = lst.get(0);
		}
		return (T) rTransactionParent;
	}
	

	// unique key loaders
	
	public static <T extends BankDepositsRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"bank deposits\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique bank deposits row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
	public static <T extends BankDepositsRow> T loadByNumber(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"bank deposits\" WHERE \"Number\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique bank deposits row by 'Number': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
}
