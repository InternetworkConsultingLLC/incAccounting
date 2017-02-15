/*
 * incGenerator - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface TransactionTypesInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setName(java.lang.String value) throws Exception;
	java.lang.String getName();
	
	boolean setEditUrl(java.lang.String value) throws Exception;
	java.lang.String getEditUrl();
	
	boolean setListUrl(java.lang.String value) throws Exception;
	java.lang.String getListUrl();
	
	boolean setIsAllowed(java.lang.Boolean value) throws Exception;
	java.lang.Boolean getIsAllowed();
	
	
	<T extends DocumentTypesRow> List<T> loadDocumentType(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends PaymentTypesRow> List<T> loadPaymentType(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends TransactionsRow> List<T> loadTransactions(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	
}
