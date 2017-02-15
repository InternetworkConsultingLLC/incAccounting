/*
 * incGenerator - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface SalesTaxesInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setContactsGuid(java.lang.String value) throws Exception;
	java.lang.String getContactsGuid();
	
	boolean setIsGroup(java.lang.Boolean value) throws Exception;
	java.lang.Boolean getIsGroup();
	
	boolean setDisplayName(java.lang.String value) throws Exception;
	java.lang.String getDisplayName();
	
	boolean setTaxRate(java.math.BigDecimal value) throws Exception;
	java.math.BigDecimal getTaxRate();
	
	boolean setAccountsGuid(java.lang.String value) throws Exception;
	java.lang.String getAccountsGuid();
	
	
	<T extends DocumentsRow> List<T> loadDocuments(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends SalesTaxMembershipsRow> List<T> loadChildTaxMemberships(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends SalesTaxMembershipsRow> List<T> loadParentTaxMemberships(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	
	<T extends ContactsRow> T loadContact(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends AccountsRow> T loadAccount(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
