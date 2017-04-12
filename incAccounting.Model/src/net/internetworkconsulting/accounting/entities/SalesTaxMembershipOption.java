package net.internetworkconsulting.accounting.entities;

import net.internetworkconsulting.accounting.data.SalesTaxesRow;

public class SalesTaxMembershipOption extends SalesTaxesRow {
	public static String IS_INCLUDED = "Is Included";
	public void setIsIncluded(boolean value) throws Exception { 
		if(value)
			set(IS_INCLUDED, (long) 1); 
		else
			set(IS_INCLUDED, (long) 0);
	}
	public boolean getIsIncluded() {
		Object objIncluded = get(IS_INCLUDED);
		return objIncluded != null && ((long) objIncluded) != 0;
	}
}
