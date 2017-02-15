/*
 * incGenerator - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface ItemsInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setParentItemsGuid(java.lang.String value) throws Exception;
	java.lang.String getParentItemsGuid();
	
	boolean setSegment(java.lang.String value) throws Exception;
	java.lang.String getSegment();
	
	boolean setNumber(java.lang.String value) throws Exception;
	java.lang.String getNumber();
	
	boolean setIsAllowed(java.lang.Boolean value) throws Exception;
	java.lang.Boolean getIsAllowed();
	
	boolean setSalesAccountsGuid(java.lang.String value) throws Exception;
	java.lang.String getSalesAccountsGuid();
	
	boolean setSalesDescription(java.lang.String value) throws Exception;
	java.lang.String getSalesDescription();
	
	boolean setSalesMarkUp(java.math.BigDecimal value) throws Exception;
	java.math.BigDecimal getSalesMarkUp();
	
	boolean setSalesUnitPrice(java.math.BigDecimal value) throws Exception;
	java.math.BigDecimal getSalesUnitPrice();
	
	boolean setIsSalesTaxed(java.lang.Boolean value) throws Exception;
	java.lang.Boolean getIsSalesTaxed();
	
	boolean setPurchaseAccountsGuid(java.lang.String value) throws Exception;
	java.lang.String getPurchaseAccountsGuid();
	
	boolean setPurchaseDescription(java.lang.String value) throws Exception;
	java.lang.String getPurchaseDescription();
	
	boolean setPurchaseContactsGuid(java.lang.String value) throws Exception;
	java.lang.String getPurchaseContactsGuid();
	
	boolean setLastUnitCost(java.math.BigDecimal value) throws Exception;
	java.math.BigDecimal getLastUnitCost();
	
	boolean setInventoryUnitMeasuresGuid(java.lang.String value) throws Exception;
	java.lang.String getInventoryUnitMeasuresGuid();
	
	boolean setInventoryAccountsGuid(java.lang.String value) throws Exception;
	java.lang.String getInventoryAccountsGuid();
	
	boolean setInventoryDescription(java.lang.String value) throws Exception;
	java.lang.String getInventoryDescription();
	
	boolean setIsSerialized(java.lang.Boolean value) throws Exception;
	java.lang.Boolean getIsSerialized();
	
	
	<T extends DocumentLinesRow> List<T> loadDocumentLines(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends ItemsRow> List<T> loadChildItems(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	
	<T extends ItemsRow> T loadParentItem(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends AccountsRow> T loadSalesAcount(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends AccountsRow> T loadPurchaseAcount(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends ContactsRow> T loadPurchaseContact(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends UnitMeasuresRow> T loadInventoryUnitMeasure(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends AccountsRow> T loadInventoryAccount(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
