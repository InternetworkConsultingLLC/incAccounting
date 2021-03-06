package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class PayrollFieldValuesRow extends Row implements PayrollFieldValuesInterface {
	public PayrollFieldValuesRow() { 
		super(); 
		setSqlTableName("payroll field values");
		setSqlSecurableGuid("50bf8e044c9f5839b79cca0a5716d728");
	}
	public static String TABLE_NAME = "payroll field values";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String PAYROLL_CHECKS_GUID = "Payroll Checks GUID";
	public boolean setPayrollChecksGuid(java.lang.String value) throws Exception { return set(PAYROLL_CHECKS_GUID, value); }
	public java.lang.String getPayrollChecksGuid() { return (java.lang.String) get(PAYROLL_CHECKS_GUID); }
	
	public static String PAYROLL_FIELDS_GUID = "Payroll Fields GUID";
	public boolean setPayrollFieldsGuid(java.lang.String value) throws Exception { return set(PAYROLL_FIELDS_GUID, value); }
	public java.lang.String getPayrollFieldsGuid() { return (java.lang.String) get(PAYROLL_FIELDS_GUID); }
	
	public static String RATE = "Rate";
	public boolean setRate(java.math.BigDecimal value) throws Exception { return set(RATE, value); }
	public java.math.BigDecimal getRate() { return (java.math.BigDecimal) get(RATE); }
	
	public static String QUANTITY = "Quantity";
	public boolean setQuantity(java.math.BigDecimal value) throws Exception { return set(QUANTITY, value); }
	public java.math.BigDecimal getQuantity() { return (java.math.BigDecimal) get(QUANTITY); }
	
	public static String TOTAL = "Total";
	public boolean setTotal(java.math.BigDecimal value) throws Exception { return set(TOTAL, value); }
	public java.math.BigDecimal getTotal() { return (java.math.BigDecimal) get(TOTAL); }
	

	// child loaders
	

	// parent loaders
	
	protected Object rPayrollCheckParent = null;
	public <T extends PayrollChecksRow> T loadPayrollCheck(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rPayrollCheckParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"payroll checks\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getPayrollChecksGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique payroll checks row by GUID (" + Statement.convertObjectToString(this.getPayrollChecksGuid(), null) + ")!");
			rPayrollCheckParent = lst.get(0);
		}
		return (T) rPayrollCheckParent;
	}
	
	protected Object rPayrollFieldParent = null;
	public <T extends PayrollFieldsRow> T loadPayrollField(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rPayrollFieldParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"payroll fields\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getPayrollFieldsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique payroll fields row by GUID (" + Statement.convertObjectToString(this.getPayrollFieldsGuid(), null) + ")!");
			rPayrollFieldParent = lst.get(0);
		}
		return (T) rPayrollFieldParent;
	}
	

	// unique key loaders
	
	public static <T extends PayrollFieldValuesRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"payroll field values\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique payroll field values row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
}
