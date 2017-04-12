/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class ItemsRow extends Row implements ItemsInterface {
	public ItemsRow() { 
		super(); 
		setSqlTableName("Items");
		setSqlSecurableGuid("9dea4016dbcc290b773ab2fae678aaa8");
	}
	public static String TABLE_NAME = "Items";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String PARENT_ITEMS_GUID = "Parent Items GUID";
	public boolean setParentItemsGuid(java.lang.String value) throws Exception { return set(PARENT_ITEMS_GUID, value); }
	public java.lang.String getParentItemsGuid() { return (java.lang.String) get(PARENT_ITEMS_GUID); }
	
	public static String SEGMENT = "Segment";
	public boolean setSegment(java.lang.String value) throws Exception { return set(SEGMENT, value); }
	public java.lang.String getSegment() { return (java.lang.String) get(SEGMENT); }
	
	public static String NUMBER = "Number";
	public boolean setNumber(java.lang.String value) throws Exception { return set(NUMBER, value); }
	public java.lang.String getNumber() { return (java.lang.String) get(NUMBER); }
	
	public static String IS_ALLOWED = "Is Allowed";
	public boolean setIsAllowed(java.lang.Boolean value) throws Exception { return set(IS_ALLOWED, value); }
	public java.lang.Boolean getIsAllowed() { return (java.lang.Boolean) get(IS_ALLOWED); }
	
	public static String SALES_ACCOUNTS_GUID = "Sales Accounts GUID";
	public boolean setSalesAccountsGuid(java.lang.String value) throws Exception { return set(SALES_ACCOUNTS_GUID, value); }
	public java.lang.String getSalesAccountsGuid() { return (java.lang.String) get(SALES_ACCOUNTS_GUID); }
	
	public static String SALES_DESCRIPTION = "Sales Description";
	public boolean setSalesDescription(java.lang.String value) throws Exception { return set(SALES_DESCRIPTION, value); }
	public java.lang.String getSalesDescription() { return (java.lang.String) get(SALES_DESCRIPTION); }
	
	public static String SALES_MARK_UP = "Sales Mark Up";
	public boolean setSalesMarkUp(java.math.BigDecimal value) throws Exception { return set(SALES_MARK_UP, value); }
	public java.math.BigDecimal getSalesMarkUp() { return (java.math.BigDecimal) get(SALES_MARK_UP); }
	
	public static String SALES_UNIT_PRICE = "Sales Unit Price";
	public boolean setSalesUnitPrice(java.math.BigDecimal value) throws Exception { return set(SALES_UNIT_PRICE, value); }
	public java.math.BigDecimal getSalesUnitPrice() { return (java.math.BigDecimal) get(SALES_UNIT_PRICE); }
	
	public static String IS_SALES_TAXED = "Is Sales Taxed";
	public boolean setIsSalesTaxed(java.lang.Boolean value) throws Exception { return set(IS_SALES_TAXED, value); }
	public java.lang.Boolean getIsSalesTaxed() { return (java.lang.Boolean) get(IS_SALES_TAXED); }
	
	public static String PURCHASE_ACCOUNTS_GUID = "Purchase Accounts GUID";
	public boolean setPurchaseAccountsGuid(java.lang.String value) throws Exception { return set(PURCHASE_ACCOUNTS_GUID, value); }
	public java.lang.String getPurchaseAccountsGuid() { return (java.lang.String) get(PURCHASE_ACCOUNTS_GUID); }
	
	public static String PURCHASE_DESCRIPTION = "Purchase Description";
	public boolean setPurchaseDescription(java.lang.String value) throws Exception { return set(PURCHASE_DESCRIPTION, value); }
	public java.lang.String getPurchaseDescription() { return (java.lang.String) get(PURCHASE_DESCRIPTION); }
	
	public static String PURCHASE_CONTACTS_GUID = "Purchase Contacts GUID";
	public boolean setPurchaseContactsGuid(java.lang.String value) throws Exception { return set(PURCHASE_CONTACTS_GUID, value); }
	public java.lang.String getPurchaseContactsGuid() { return (java.lang.String) get(PURCHASE_CONTACTS_GUID); }
	
	public static String LAST_UNIT_COST = "Last Unit Cost";
	public boolean setLastUnitCost(java.math.BigDecimal value) throws Exception { return set(LAST_UNIT_COST, value); }
	public java.math.BigDecimal getLastUnitCost() { return (java.math.BigDecimal) get(LAST_UNIT_COST); }
	
	public static String INVENTORY_UNIT_MEASURES_GUID = "Inventory Unit Measures GUID";
	public boolean setInventoryUnitMeasuresGuid(java.lang.String value) throws Exception { return set(INVENTORY_UNIT_MEASURES_GUID, value); }
	public java.lang.String getInventoryUnitMeasuresGuid() { return (java.lang.String) get(INVENTORY_UNIT_MEASURES_GUID); }
	
	public static String INVENTORY_ACCOUNTS_GUID = "Inventory Accounts GUID";
	public boolean setInventoryAccountsGuid(java.lang.String value) throws Exception { return set(INVENTORY_ACCOUNTS_GUID, value); }
	public java.lang.String getInventoryAccountsGuid() { return (java.lang.String) get(INVENTORY_ACCOUNTS_GUID); }
	
	public static String INVENTORY_DESCRIPTION = "Inventory Description";
	public boolean setInventoryDescription(java.lang.String value) throws Exception { return set(INVENTORY_DESCRIPTION, value); }
	public java.lang.String getInventoryDescription() { return (java.lang.String) get(INVENTORY_DESCRIPTION); }
	
	public static String IS_SERIALIZED = "Is Serialized";
	public boolean setIsSerialized(java.lang.Boolean value) throws Exception { return set(IS_SERIALIZED, value); }
	public java.lang.Boolean getIsSerialized() { return (java.lang.Boolean) get(IS_SERIALIZED); }
	

	// child loaders
	
	protected Object lstDocumentLinesChildren = null;
	public <T extends DocumentLinesRow> List<T> loadDocumentLines(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstDocumentLinesChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Document Lines\" WHERE \"Items GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstDocumentLinesChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstDocumentLinesChildren;
	}
	
	protected Object lstChildItemsChildren = null;
	public <T extends ItemsRow> List<T> loadChildItems(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstChildItemsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Items\" WHERE \"Parent Items GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstChildItemsChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstChildItemsChildren;
	}
	

	// parent loaders
	
	protected Object rParentItemParent = null;
	public <T extends ItemsRow> T loadParentItem(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rParentItemParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Items\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getParentItemsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique Items row by GUID (" + Statement.convertObjectToString(this.getParentItemsGuid(), null) + ")!");
			rParentItemParent = lst.get(0);
		}
		return (T) rParentItemParent;
	}
	
	protected Object rSalesAcountParent = null;
	public <T extends AccountsRow> T loadSalesAcount(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rSalesAcountParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Accounts\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getSalesAccountsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique Accounts row by GUID (" + Statement.convertObjectToString(this.getSalesAccountsGuid(), null) + ")!");
			rSalesAcountParent = lst.get(0);
		}
		return (T) rSalesAcountParent;
	}
	
	protected Object rPurchaseAcountParent = null;
	public <T extends AccountsRow> T loadPurchaseAcount(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rPurchaseAcountParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Accounts\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getPurchaseAccountsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique Accounts row by GUID (" + Statement.convertObjectToString(this.getPurchaseAccountsGuid(), null) + ")!");
			rPurchaseAcountParent = lst.get(0);
		}
		return (T) rPurchaseAcountParent;
	}
	
	protected Object rPurchaseContactParent = null;
	public <T extends ContactsRow> T loadPurchaseContact(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rPurchaseContactParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Contacts\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getPurchaseContactsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique Contacts row by GUID (" + Statement.convertObjectToString(this.getPurchaseContactsGuid(), null) + ")!");
			rPurchaseContactParent = lst.get(0);
		}
		return (T) rPurchaseContactParent;
	}
	
	protected Object rInventoryUnitMeasureParent = null;
	public <T extends UnitMeasuresRow> T loadInventoryUnitMeasure(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rInventoryUnitMeasureParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Unit Measures\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getInventoryUnitMeasuresGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique Unit Measures row by GUID (" + Statement.convertObjectToString(this.getInventoryUnitMeasuresGuid(), null) + ")!");
			rInventoryUnitMeasureParent = lst.get(0);
		}
		return (T) rInventoryUnitMeasureParent;
	}
	
	protected Object rInventoryAccountParent = null;
	public <T extends AccountsRow> T loadInventoryAccount(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rInventoryAccountParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Accounts\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getInventoryAccountsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique Accounts row by GUID (" + Statement.convertObjectToString(this.getInventoryAccountsGuid(), null) + ")!");
			rInventoryAccountParent = lst.get(0);
		}
		return (T) rInventoryAccountParent;
	}
	

	// unique key loaders
	
	public static <T extends ItemsRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"Items\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique Items row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
	public static <T extends ItemsRow> T loadByNumber(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"Items\" WHERE \"Number\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique Items row by 'Number': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	

	// load all
	public static <T extends ItemsRow> List<T> loadAll(AdapterInterface adapter, Class model) throws Exception {
		Statement stmt = new Statement("SELECT * FROM \"Items\"");
		return (List<T>) adapter.load(model, stmt, true);
	}
}
