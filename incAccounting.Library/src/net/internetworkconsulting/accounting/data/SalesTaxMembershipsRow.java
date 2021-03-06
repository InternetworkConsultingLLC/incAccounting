package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class SalesTaxMembershipsRow extends Row implements SalesTaxMembershipsInterface {
	public SalesTaxMembershipsRow() { 
		super(); 
		setSqlTableName("sales tax memberships");
		setSqlSecurableGuid("6f0e8a9adaef933d118da911c511595f");
	}
	public static String TABLE_NAME = "sales tax memberships";

	// columns
	
	public static String PARENT_SALES_TAXES_GUID = "Parent Sales Taxes GUID";
	public boolean setParentSalesTaxesGuid(java.lang.String value) throws Exception { return set(PARENT_SALES_TAXES_GUID, value); }
	public java.lang.String getParentSalesTaxesGuid() { return (java.lang.String) get(PARENT_SALES_TAXES_GUID); }
	
	public static String CHILD_SALES_TAXES_GUID = "Child Sales Taxes GUID";
	public boolean setChildSalesTaxesGuid(java.lang.String value) throws Exception { return set(CHILD_SALES_TAXES_GUID, value); }
	public java.lang.String getChildSalesTaxesGuid() { return (java.lang.String) get(CHILD_SALES_TAXES_GUID); }
	

	// child loaders
	

	// parent loaders
	
	protected Object rParentSalesTaxParent = null;
	public <T extends SalesTaxesRow> T loadParentSalesTax(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rParentSalesTaxParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"sales taxes\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getParentSalesTaxesGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique sales taxes row by GUID (" + Statement.convertObjectToString(this.getParentSalesTaxesGuid(), null) + ")!");
			rParentSalesTaxParent = lst.get(0);
		}
		return (T) rParentSalesTaxParent;
	}
	
	protected Object rChildSalesTaxParent = null;
	public <T extends SalesTaxesRow> T loadChildSalesTax(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rChildSalesTaxParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"sales taxes\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getChildSalesTaxesGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique sales taxes row by GUID (" + Statement.convertObjectToString(this.getChildSalesTaxesGuid(), null) + ")!");
			rChildSalesTaxParent = lst.get(0);
		}
		return (T) rChildSalesTaxParent;
	}
	

	// unique key loaders
	
}
