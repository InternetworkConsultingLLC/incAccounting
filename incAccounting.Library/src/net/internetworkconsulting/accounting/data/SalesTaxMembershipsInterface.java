package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface SalesTaxMembershipsInterface {
	
	boolean setParentSalesTaxesGuid(java.lang.String value) throws Exception;
	java.lang.String getParentSalesTaxesGuid();
	
	boolean setChildSalesTaxesGuid(java.lang.String value) throws Exception;
	java.lang.String getChildSalesTaxesGuid();
	
	
	
	<T extends SalesTaxesRow> T loadParentSalesTax(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends SalesTaxesRow> T loadChildSalesTax(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
