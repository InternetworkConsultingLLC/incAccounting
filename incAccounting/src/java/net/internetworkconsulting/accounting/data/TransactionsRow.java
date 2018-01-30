/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class TransactionsRow extends Row implements TransactionsInterface {
	public TransactionsRow() { 
		super(); 
		setSqlTableName("transactions");
		setSqlSecurableGuid("c15b977dd99332ca8623fbdfb86827e8");
	}
	public static String TABLE_NAME = "transactions";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String DATE = "Date";
	public boolean setDate(java.sql.Date value) throws Exception { return set(DATE, value); }
	public java.sql.Date getDate() { return (java.sql.Date) get(DATE); }
	
	public static String REFERENCE_NUMBER = "Reference Number";
	public boolean setReferenceNumber(java.lang.String value) throws Exception { return set(REFERENCE_NUMBER, value); }
	public java.lang.String getReferenceNumber() { return (java.lang.String) get(REFERENCE_NUMBER); }
	
	public static String TRANSACTION_TYPES_GUID = "Transaction Types GUID";
	public boolean setTransactionTypesGuid(java.lang.String value) throws Exception { return set(TRANSACTION_TYPES_GUID, value); }
	public java.lang.String getTransactionTypesGuid() { return (java.lang.String) get(TRANSACTION_TYPES_GUID); }
	

	// child loaders
	
	protected Object lstBankDepositChildren = null;
	public <T extends BankDepositsRow> List<T> loadBankDeposit(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstBankDepositChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"bank deposits\" WHERE \"Posted Transactions GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstBankDepositChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstBankDepositChildren;
	}
	
	protected Object lstDocumentChildren = null;
	public <T extends DocumentsRow> List<T> loadDocument(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstDocumentChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"documents\" WHERE \"Posted Transactions GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstDocumentChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstDocumentChildren;
	}
	
	protected Object lstPaymentChildren = null;
	public <T extends PaymentsRow> List<T> loadPayment(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstPaymentChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"payments\" WHERE \"Posted Transactions GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstPaymentChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstPaymentChildren;
	}
	
	protected Object lstPayrollChecksChildren = null;
	public <T extends PayrollChecksRow> List<T> loadPayrollChecks(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstPayrollChecksChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"payroll checks\" WHERE \"Posted Transactions GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstPayrollChecksChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstPayrollChecksChildren;
	}
	
	protected Object lstTransactionLinesChildren = null;
	public <T extends TransactionLinesRow> List<T> loadTransactionLines(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstTransactionLinesChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"transaction lines\" WHERE \"Transactions GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstTransactionLinesChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstTransactionLinesChildren;
	}
	

	// parent loaders
	
	protected Object rTransactionTypeParent = null;
	public <T extends TransactionTypesRow> T loadTransactionType(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rTransactionTypeParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"transaction types\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getTransactionTypesGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique transaction types row by GUID (" + Statement.convertObjectToString(this.getTransactionTypesGuid(), null) + ")!");
			rTransactionTypeParent = lst.get(0);
		}
		return (T) rTransactionTypeParent;
	}
	

	// unique key loaders
	
	public static <T extends TransactionsRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"transactions\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique transactions row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
}
