/*
 * incGenerator - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface PayrollFieldsInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setName(java.lang.String value) throws Exception;
	java.lang.String getName();
	
	boolean setPayrollFieldTypesGuid(java.lang.String value) throws Exception;
	java.lang.String getPayrollFieldTypesGuid();
	
	boolean setDebitAccountsGuid(java.lang.String value) throws Exception;
	java.lang.String getDebitAccountsGuid();
	
	boolean setCreditAccountsGuid(java.lang.String value) throws Exception;
	java.lang.String getCreditAccountsGuid();
	
	
	<T extends PayrollFieldValuesRow> List<T> loadPayrollFieldValues(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	
	<T extends PayrollFieldTypesRow> T loadPayrollFieldType(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends AccountsRow> T loadDebitAccount(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends AccountsRow> T loadCreditAccount(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
