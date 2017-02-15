/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import net.internetworkconsulting.data.mysql.Statement;
import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;

public class PayrollFieldTypesRow extends Row implements PayrollFieldTypesInterface {
	public PayrollFieldTypesRow() { 
		super(); 
		setSqlTableName("payroll field types");
		setSqlSecurableGuid("a2f681c2afc34bd597b3a3eb716f37f3");
	}
	public static String TABLE_NAME = "payroll field types";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String NAME = "Name";
	public boolean setName(java.lang.String value) throws Exception { return set(NAME, value); }
	public java.lang.String getName() { return (java.lang.String) get(NAME); }
	
	public static String DEBIT_REQUIRED = "Debit Required";
	public boolean setDebitRequired(java.lang.Boolean value) throws Exception { return set(DEBIT_REQUIRED, value); }
	public java.lang.Boolean getDebitRequired() { return (java.lang.Boolean) get(DEBIT_REQUIRED); }
	
	public static String CREDIT_REQUIRED = "Credit Required";
	public boolean setCreditRequired(java.lang.Boolean value) throws Exception { return set(CREDIT_REQUIRED, value); }
	public java.lang.Boolean getCreditRequired() { return (java.lang.Boolean) get(CREDIT_REQUIRED); }
	

	// child loaders
	
	protected Object lstPayrollFieldsChildren = null;
	public <T extends PayrollFieldsRow> List<T> loadPayrollFields(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstPayrollFieldsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"payroll fields\" WHERE \"Payroll Field Types GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstPayrollFieldsChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstPayrollFieldsChildren;
	}
	

	// parent loaders
	

	// unique key loaders
	
	public static <T extends PayrollFieldTypesRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"payroll field types\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique payroll field types row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
	public static <T extends PayrollFieldTypesRow> T loadByName(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"payroll field types\" WHERE \"Name\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique payroll field types row by 'Name': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	

	// load all
	public static <T extends PayrollFieldTypesRow> List<T> loadAll(AdapterInterface adapter, Class model) throws Exception {
		Statement stmt = new Statement("SELECT * FROM \"payroll fields\"");
		return (List<T>) adapter.load(model, stmt);
	}
}
