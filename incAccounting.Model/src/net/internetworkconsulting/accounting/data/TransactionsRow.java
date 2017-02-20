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
		setSqlTableName("Transactions");
		setSqlSecurableGuid("31112aca11d0e9e6eb7db96f317dda49");
	}
	public static String TABLE_NAME = "Transactions";

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
			Statement stmt = new Statement("SELECT * FROM \"Bank Deposits\" WHERE \"Posted Transactions GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstBankDepositChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstBankDepositChildren;
	}
	
	protected Object lstDocumentChildren = null;
	public <T extends DocumentsRow> List<T> loadDocument(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstDocumentChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Documents\" WHERE \"Posted Transactions GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstDocumentChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstDocumentChildren;
	}
	
	protected Object lstPaymentChildren = null;
	public <T extends PaymentsRow> List<T> loadPayment(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstPaymentChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Payments\" WHERE \"Posted Transactions GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstPaymentChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstPaymentChildren;
	}
	
	protected Object lstPayrollChecksChildren = null;
	public <T extends PayrollChecksRow> List<T> loadPayrollChecks(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstPayrollChecksChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Payroll Checks\" WHERE \"Posted Transactions GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstPayrollChecksChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstPayrollChecksChildren;
	}
	
	protected Object lstTransactionLinesChildren = null;
	public <T extends TransactionLinesRow> List<T> loadTransactionLines(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstTransactionLinesChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Transaction Lines\" WHERE \"Transactions GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstTransactionLinesChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstTransactionLinesChildren;
	}
	

	// parent loaders
	
	protected Object rTransactionTypeParent = null;
	public <T extends TransactionTypesRow> T loadTransactionType(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rTransactionTypeParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Transaction Types\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getTransactionTypesGuid());
			List<T> lst = adapter.load(model, stmt);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique Transaction Types row by GUID (" + Statement.convertObjectToString(this.getTransactionTypesGuid(), null) + ")!");
			rTransactionTypeParent = lst.get(0);
		}
		return (T) rTransactionTypeParent;
	}
	

	// unique key loaders
	
	public static <T extends TransactionsRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"Transactions\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique Transactions row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	

	// load all
	public static <T extends TransactionsRow> List<T> loadAll(AdapterInterface adapter, Class model) throws Exception {
		Statement stmt = new Statement("SELECT * FROM \"Transactions\"");
		return (List<T>) adapter.load(model, stmt);
	}
}
