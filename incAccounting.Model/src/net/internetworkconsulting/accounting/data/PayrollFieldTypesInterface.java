/*
 * incGenerator - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface PayrollFieldTypesInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setName(java.lang.String value) throws Exception;
	java.lang.String getName();
	
	boolean setDebitRequired(java.lang.Boolean value) throws Exception;
	java.lang.Boolean getDebitRequired();
	
	boolean setCreditRequired(java.lang.Boolean value) throws Exception;
	java.lang.Boolean getCreditRequired();
	
	
	<T extends PayrollFieldsRow> List<T> loadPayrollFields(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	
}
