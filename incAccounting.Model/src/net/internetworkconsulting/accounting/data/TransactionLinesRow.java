/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class TransactionLinesRow extends Row implements TransactionLinesInterface {
	public TransactionLinesRow() { 
		super(); 
		setSqlTableName("transaction lines");
		setSqlSecurableGuid("ca9e47d13b37bc3dbb9c9f4feaab8398");
	}
	public static String TABLE_NAME = "transaction lines";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String TRANSACTIONS_GUID = "Transactions GUID";
	public boolean setTransactionsGuid(java.lang.String value) throws Exception { return set(TRANSACTIONS_GUID, value); }
	public java.lang.String getTransactionsGuid() { return (java.lang.String) get(TRANSACTIONS_GUID); }
	
	public static String SORT_ORDER = "Sort Order";
	public boolean setSortOrder(java.lang.Integer value) throws Exception { return set(SORT_ORDER, value); }
	public java.lang.Integer getSortOrder() { return (java.lang.Integer) get(SORT_ORDER); }
	
	public static String DESCRIPTION = "Description";
	public boolean setDescription(java.lang.String value) throws Exception { return set(DESCRIPTION, value); }
	public java.lang.String getDescription() { return (java.lang.String) get(DESCRIPTION); }
	
	public static String DEBIT = "Debit";
	public boolean setDebit(java.math.BigDecimal value) throws Exception { return set(DEBIT, value); }
	public java.math.BigDecimal getDebit() { return (java.math.BigDecimal) get(DEBIT); }
	
	public static String JOBS_GUID = "Jobs GUID";
	public boolean setJobsGuid(java.lang.String value) throws Exception { return set(JOBS_GUID, value); }
	public java.lang.String getJobsGuid() { return (java.lang.String) get(JOBS_GUID); }
	
	public static String DEPARTMENTS_GUID = "Departments GUID";
	public boolean setDepartmentsGuid(java.lang.String value) throws Exception { return set(DEPARTMENTS_GUID, value); }
	public java.lang.String getDepartmentsGuid() { return (java.lang.String) get(DEPARTMENTS_GUID); }
	
	public static String ACCOUNTS_GUID = "Accounts GUID";
	public boolean setAccountsGuid(java.lang.String value) throws Exception { return set(ACCOUNTS_GUID, value); }
	public java.lang.String getAccountsGuid() { return (java.lang.String) get(ACCOUNTS_GUID); }
	
	public static String RECONCILIATIONS_GUID = "Reconciliations GUID";
	public boolean setReconciliationsGuid(java.lang.String value) throws Exception { return set(RECONCILIATIONS_GUID, value); }
	public java.lang.String getReconciliationsGuid() { return (java.lang.String) get(RECONCILIATIONS_GUID); }
	

	// child loaders
	
	protected Object lstRegisterEntriesChildren = null;
	public <T extends RegisterEntriesRow> List<T> loadRegisterEntries(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstRegisterEntriesChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"register entries\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstRegisterEntriesChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstRegisterEntriesChildren;
	}
	

	// parent loaders
	
	protected Object rTransactionParent = null;
	public <T extends TransactionsRow> T loadTransaction(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rTransactionParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"transactions\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getTransactionsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique transactions row by GUID (" + Statement.convertObjectToString(this.getTransactionsGuid(), null) + ")!");
			rTransactionParent = lst.get(0);
		}
		return (T) rTransactionParent;
	}
	
	protected Object rJobParent = null;
	public <T extends JobsRow> T loadJob(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rJobParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"jobs\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getJobsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique jobs row by GUID (" + Statement.convertObjectToString(this.getJobsGuid(), null) + ")!");
			rJobParent = lst.get(0);
		}
		return (T) rJobParent;
	}
	
	protected Object rDepartmentParent = null;
	public <T extends DepartmentsRow> T loadDepartment(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rDepartmentParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"departments\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getDepartmentsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique departments row by GUID (" + Statement.convertObjectToString(this.getDepartmentsGuid(), null) + ")!");
			rDepartmentParent = lst.get(0);
		}
		return (T) rDepartmentParent;
	}
	
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
	
	protected Object rReconciliationParent = null;
	public <T extends ReconciliationsRow> T loadReconciliation(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rReconciliationParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"reconciliations\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getReconciliationsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique reconciliations row by GUID (" + Statement.convertObjectToString(this.getReconciliationsGuid(), null) + ")!");
			rReconciliationParent = lst.get(0);
		}
		return (T) rReconciliationParent;
	}
	

	// unique key loaders
	
	public static <T extends TransactionLinesRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"transaction lines\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique transaction lines row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
}
