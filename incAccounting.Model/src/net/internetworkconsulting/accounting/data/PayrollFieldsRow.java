/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import net.internetworkconsulting.data.mysql.Statement;
import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;

public class PayrollFieldsRow extends Row implements PayrollFieldsInterface {
	public PayrollFieldsRow() { 
		super(); 
		setSqlTableName("payroll fields");
		setSqlSecurableGuid("1b8a2c949640913326cf4de864dd546f");
	}
	public static String TABLE_NAME = "payroll fields";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String NAME = "Name";
	public boolean setName(java.lang.String value) throws Exception { return set(NAME, value); }
	public java.lang.String getName() { return (java.lang.String) get(NAME); }
	
	public static String PAYROLL_FIELD_TYPES_GUID = "Payroll Field Types GUID";
	public boolean setPayrollFieldTypesGuid(java.lang.String value) throws Exception { return set(PAYROLL_FIELD_TYPES_GUID, value); }
	public java.lang.String getPayrollFieldTypesGuid() { return (java.lang.String) get(PAYROLL_FIELD_TYPES_GUID); }
	
	public static String DEBIT_ACCOUNTS_GUID = "Debit Accounts GUID";
	public boolean setDebitAccountsGuid(java.lang.String value) throws Exception { return set(DEBIT_ACCOUNTS_GUID, value); }
	public java.lang.String getDebitAccountsGuid() { return (java.lang.String) get(DEBIT_ACCOUNTS_GUID); }
	
	public static String CREDIT_ACCOUNTS_GUID = "Credit Accounts GUID";
	public boolean setCreditAccountsGuid(java.lang.String value) throws Exception { return set(CREDIT_ACCOUNTS_GUID, value); }
	public java.lang.String getCreditAccountsGuid() { return (java.lang.String) get(CREDIT_ACCOUNTS_GUID); }
	

	// child loaders
	
	protected Object lstPayrollFieldValuesChildren = null;
	public <T extends PayrollFieldValuesRow> List<T> loadPayrollFieldValues(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstPayrollFieldValuesChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"payroll field values\" WHERE \"Payroll Fields GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstPayrollFieldValuesChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstPayrollFieldValuesChildren;
	}
	

	// parent loaders
	
	protected Object rPayrollFieldTypeParent = null;
	public <T extends PayrollFieldTypesRow> T loadPayrollFieldType(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rPayrollFieldTypeParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"payroll field types\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getPayrollFieldTypesGuid());
			List<T> lst = adapter.load(model, stmt);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique payroll field types row by GUID (" + Statement.convertObjectToString(this.getPayrollFieldTypesGuid(), null) + ")!");
			rPayrollFieldTypeParent = lst.get(0);
		}
		return (T) rPayrollFieldTypeParent;
	}
	
	protected Object rDebitAccountParent = null;
	public <T extends AccountsRow> T loadDebitAccount(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rDebitAccountParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"accounts\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getDebitAccountsGuid());
			List<T> lst = adapter.load(model, stmt);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique accounts row by GUID (" + Statement.convertObjectToString(this.getDebitAccountsGuid(), null) + ")!");
			rDebitAccountParent = lst.get(0);
		}
		return (T) rDebitAccountParent;
	}
	
	protected Object rCreditAccountParent = null;
	public <T extends AccountsRow> T loadCreditAccount(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rCreditAccountParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"accounts\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getCreditAccountsGuid());
			List<T> lst = adapter.load(model, stmt);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique accounts row by GUID (" + Statement.convertObjectToString(this.getCreditAccountsGuid(), null) + ")!");
			rCreditAccountParent = lst.get(0);
		}
		return (T) rCreditAccountParent;
	}
	

	// unique key loaders
	
	public static <T extends PayrollFieldsRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"payroll fields\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique payroll fields row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
	public static <T extends PayrollFieldsRow> T loadByName(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"payroll fields\" WHERE \"Name\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique payroll fields row by 'Name': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	

	// load all
	public static <T extends PayrollFieldsRow> List<T> loadAll(AdapterInterface adapter, Class model) throws Exception {
		Statement stmt = new Statement("SELECT * FROM \"payroll fields\"");
		return (List<T>) adapter.load(model, stmt);
	}
}
