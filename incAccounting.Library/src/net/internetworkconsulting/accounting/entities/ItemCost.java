
package net.internetworkconsulting.accounting.entities;

import java.util.List;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.Statement;

public class ItemCost extends Row {
	static List<ItemCost> loadForItem(AdapterInterface adapter, String item_guid) throws Exception {
		Statement stmt = new Statement(adapter.getSession().readJar(ItemCost.class, "ItemCost.loadForItem.sql"));		
		stmt.getParameters().put("{Items GUID}", item_guid);
		return adapter.load(ItemCost.class, stmt, true);
	}
	
	public ItemCost() { 
		super(); 
		setSqlTableName("Item Costs");
		setSqlSecurableGuid("0d81dd0563f640948969b7d69c8d6eb5");
	}
	public static String TABLE_NAME = "Item Costs";

	public static String DOCUMENT_LINES_GUID = "Document Lines GUID";
	public boolean setDocumentLinesGUID(java.lang.String value) throws Exception { return set(DOCUMENT_LINES_GUID, value); }
	public java.lang.String getDocumentLinesGUID() { return (java.lang.String) get(DOCUMENT_LINES_GUID); }
	
	public static String ITEMS_GUID = "Items GUID";
	public boolean setItemsGUID(java.lang.String value) throws Exception { return set(ITEMS_GUID, value); }
	public java.lang.String getItemsGUID() { return (java.lang.String) get(ITEMS_GUID); }
	
	public static String AVAILABLE = "Available";
	public boolean setAvailable(java.math.BigDecimal value) throws Exception { return set(AVAILABLE, value); }
	public java.math.BigDecimal getAvailable() { return (java.math.BigDecimal) get(AVAILABLE); }
	
	public static String UNIT_COST = "Unit Cost";
	public boolean setUnitCost(java.math.BigDecimal value) throws Exception { return set(UNIT_COST, value); }
	public java.math.BigDecimal getUnitCost() { return (java.math.BigDecimal) get(UNIT_COST); }
}
