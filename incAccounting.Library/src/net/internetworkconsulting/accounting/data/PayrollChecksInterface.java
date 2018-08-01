package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface PayrollChecksInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setIsTemplate(java.lang.Boolean value) throws Exception;
	java.lang.Boolean getIsTemplate();
	
	boolean setNumber(java.lang.String value) throws Exception;
	java.lang.String getNumber();
	
	boolean setDate(java.util.Date value) throws Exception;
	java.util.Date getDate();
	
	boolean setEmployeesGuid(java.lang.String value) throws Exception;
	java.lang.String getEmployeesGuid();
	
	boolean setPayToTheOrderOf(java.lang.String value) throws Exception;
	java.lang.String getPayToTheOrderOf();
	
	boolean setLine1(java.lang.String value) throws Exception;
	java.lang.String getLine1();
	
	boolean setLine2(java.lang.String value) throws Exception;
	java.lang.String getLine2();
	
	boolean setCountry(java.lang.String value) throws Exception;
	java.lang.String getCountry();
	
	boolean setCity(java.lang.String value) throws Exception;
	java.lang.String getCity();
	
	boolean setState(java.lang.String value) throws Exception;
	java.lang.String getState();
	
	boolean setPostalCode(java.lang.String value) throws Exception;
	java.lang.String getPostalCode();
	
	boolean setAccountsGuid(java.lang.String value) throws Exception;
	java.lang.String getAccountsGuid();
	
	boolean setPostedTransactionsGuid(java.lang.String value) throws Exception;
	java.lang.String getPostedTransactionsGuid();
	
	boolean setCompensation(java.math.BigDecimal value) throws Exception;
	java.math.BigDecimal getCompensation();
	
	boolean setAdjustedGross(java.math.BigDecimal value) throws Exception;
	java.math.BigDecimal getAdjustedGross();
	
	boolean setEmployeePaid(java.math.BigDecimal value) throws Exception;
	java.math.BigDecimal getEmployeePaid();
	
	boolean setPaycheckAmount(java.math.BigDecimal value) throws Exception;
	java.math.BigDecimal getPaycheckAmount();
	
	boolean setCompanyPaid(java.math.BigDecimal value) throws Exception;
	java.math.BigDecimal getCompanyPaid();
	
	boolean setTotalCosts(java.math.BigDecimal value) throws Exception;
	java.math.BigDecimal getTotalCosts();
	
	boolean setDuration(java.lang.Integer value) throws Exception;
	java.lang.Integer getDuration();
	
	boolean setEnding(java.util.Date value) throws Exception;
	java.util.Date getEnding();
	
	
	<T extends PayrollFieldValuesRow> List<T> loadPayrollFieldValues(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	
	<T extends EmployeesRow> T loadEmployeeGuid(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends AccountsRow> T loadAccountsGuid(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends TransactionsRow> T loadTransaction(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
