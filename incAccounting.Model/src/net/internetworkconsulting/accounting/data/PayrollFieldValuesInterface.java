/*
 * incGenerator - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface PayrollFieldValuesInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setPayrollChecksGuid(java.lang.String value) throws Exception;
	java.lang.String getPayrollChecksGuid();
	
	boolean setPayrollFieldsGuid(java.lang.String value) throws Exception;
	java.lang.String getPayrollFieldsGuid();
	
	boolean setRate(java.math.BigDecimal value) throws Exception;
	java.math.BigDecimal getRate();
	
	boolean setQuantity(java.math.BigDecimal value) throws Exception;
	java.math.BigDecimal getQuantity();
	
	boolean setTotal(java.math.BigDecimal value) throws Exception;
	java.math.BigDecimal getTotal();
	
	
	
	<T extends PayrollChecksRow> T loadPayrollCheck(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends PayrollFieldsRow> T loadPayrollField(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
