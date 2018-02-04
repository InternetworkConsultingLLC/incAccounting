/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class PayrollChecksRow extends Row implements PayrollChecksInterface {
	public PayrollChecksRow() { 
		super(); 
		setSqlTableName("payroll checks");
		setSqlSecurableGuid("6a8d29d88960159f14c21875ee8b5e54");
	}
	public static String TABLE_NAME = "payroll checks";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String IS_TEMPLATE = "Is Template";
	public boolean setIsTemplate(java.lang.Boolean value) throws Exception { return set(IS_TEMPLATE, value); }
	public java.lang.Boolean getIsTemplate() { return (java.lang.Boolean) get(IS_TEMPLATE); }
	
	public static String NUMBER = "Number";
	public boolean setNumber(java.lang.String value) throws Exception { return set(NUMBER, value); }
	public java.lang.String getNumber() { return (java.lang.String) get(NUMBER); }
	
	public static String DATE = "Date";
	public boolean setDate(java.util.Date value) throws Exception { return set(DATE, value); }
	public java.util.Date getDate() { return (java.util.Date) get(DATE); }
	
	public static String EMPLOYEES_GUID = "Employees GUID";
	public boolean setEmployeesGuid(java.lang.String value) throws Exception { return set(EMPLOYEES_GUID, value); }
	public java.lang.String getEmployeesGuid() { return (java.lang.String) get(EMPLOYEES_GUID); }
	
	public static String PAY_TO_THE_ORDER_OF = "Pay To The Order Of";
	public boolean setPayToTheOrderOf(java.lang.String value) throws Exception { return set(PAY_TO_THE_ORDER_OF, value); }
	public java.lang.String getPayToTheOrderOf() { return (java.lang.String) get(PAY_TO_THE_ORDER_OF); }
	
	public static String LINE_1 = "Line 1";
	public boolean setLine1(java.lang.String value) throws Exception { return set(LINE_1, value); }
	public java.lang.String getLine1() { return (java.lang.String) get(LINE_1); }
	
	public static String LINE_2 = "Line 2";
	public boolean setLine2(java.lang.String value) throws Exception { return set(LINE_2, value); }
	public java.lang.String getLine2() { return (java.lang.String) get(LINE_2); }
	
	public static String COUNTRY = "Country";
	public boolean setCountry(java.lang.String value) throws Exception { return set(COUNTRY, value); }
	public java.lang.String getCountry() { return (java.lang.String) get(COUNTRY); }
	
	public static String CITY = "City";
	public boolean setCity(java.lang.String value) throws Exception { return set(CITY, value); }
	public java.lang.String getCity() { return (java.lang.String) get(CITY); }
	
	public static String STATE = "State";
	public boolean setState(java.lang.String value) throws Exception { return set(STATE, value); }
	public java.lang.String getState() { return (java.lang.String) get(STATE); }
	
	public static String POSTAL_CODE = "Postal Code";
	public boolean setPostalCode(java.lang.String value) throws Exception { return set(POSTAL_CODE, value); }
	public java.lang.String getPostalCode() { return (java.lang.String) get(POSTAL_CODE); }
	
	public static String ACCOUNTS_GUID = "Accounts GUID";
	public boolean setAccountsGuid(java.lang.String value) throws Exception { return set(ACCOUNTS_GUID, value); }
	public java.lang.String getAccountsGuid() { return (java.lang.String) get(ACCOUNTS_GUID); }
	
	public static String POSTED_TRANSACTIONS_GUID = "Posted Transactions GUID";
	public boolean setPostedTransactionsGuid(java.lang.String value) throws Exception { return set(POSTED_TRANSACTIONS_GUID, value); }
	public java.lang.String getPostedTransactionsGuid() { return (java.lang.String) get(POSTED_TRANSACTIONS_GUID); }
	
	public static String COMPENSATION = "Compensation";
	public boolean setCompensation(java.math.BigDecimal value) throws Exception { return set(COMPENSATION, value); }
	public java.math.BigDecimal getCompensation() { return (java.math.BigDecimal) get(COMPENSATION); }
	
	public static String ADJUSTED_GROSS = "Adjusted Gross";
	public boolean setAdjustedGross(java.math.BigDecimal value) throws Exception { return set(ADJUSTED_GROSS, value); }
	public java.math.BigDecimal getAdjustedGross() { return (java.math.BigDecimal) get(ADJUSTED_GROSS); }
	
	public static String EMPLOYEE_PAID = "Employee Paid";
	public boolean setEmployeePaid(java.math.BigDecimal value) throws Exception { return set(EMPLOYEE_PAID, value); }
	public java.math.BigDecimal getEmployeePaid() { return (java.math.BigDecimal) get(EMPLOYEE_PAID); }
	
	public static String PAYCHECK_AMOUNT = "Paycheck Amount";
	public boolean setPaycheckAmount(java.math.BigDecimal value) throws Exception { return set(PAYCHECK_AMOUNT, value); }
	public java.math.BigDecimal getPaycheckAmount() { return (java.math.BigDecimal) get(PAYCHECK_AMOUNT); }
	
	public static String COMPANY_PAID = "Company Paid";
	public boolean setCompanyPaid(java.math.BigDecimal value) throws Exception { return set(COMPANY_PAID, value); }
	public java.math.BigDecimal getCompanyPaid() { return (java.math.BigDecimal) get(COMPANY_PAID); }
	
	public static String TOTAL_COSTS = "Total Costs";
	public boolean setTotalCosts(java.math.BigDecimal value) throws Exception { return set(TOTAL_COSTS, value); }
	public java.math.BigDecimal getTotalCosts() { return (java.math.BigDecimal) get(TOTAL_COSTS); }
	
	public static String DURATION = "Duration";
	public boolean setDuration(java.lang.Integer value) throws Exception { return set(DURATION, value); }
	public java.lang.Integer getDuration() { return (java.lang.Integer) get(DURATION); }
	
	public static String ENDING = "Ending";
	public boolean setEnding(java.util.Date value) throws Exception { return set(ENDING, value); }
	public java.util.Date getEnding() { return (java.util.Date) get(ENDING); }
	

	// child loaders
	
	protected Object lstPayrollFieldValuesChildren = null;
	public <T extends PayrollFieldValuesRow> List<T> loadPayrollFieldValues(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstPayrollFieldValuesChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"payroll field values\" WHERE \"Payroll Checks GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstPayrollFieldValuesChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstPayrollFieldValuesChildren;
	}
	

	// parent loaders
	
	protected Object rEmployeeGuidParent = null;
	public <T extends EmployeesRow> T loadEmployeeGuid(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rEmployeeGuidParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"employees\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getEmployeesGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique employees row by GUID (" + Statement.convertObjectToString(this.getEmployeesGuid(), null) + ")!");
			rEmployeeGuidParent = lst.get(0);
		}
		return (T) rEmployeeGuidParent;
	}
	
	protected Object rAccountsGuidParent = null;
	public <T extends AccountsRow> T loadAccountsGuid(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rAccountsGuidParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"accounts\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getAccountsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique accounts row by GUID (" + Statement.convertObjectToString(this.getAccountsGuid(), null) + ")!");
			rAccountsGuidParent = lst.get(0);
		}
		return (T) rAccountsGuidParent;
	}
	
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
	
	public static <T extends PayrollChecksRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"payroll checks\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique payroll checks row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
}
