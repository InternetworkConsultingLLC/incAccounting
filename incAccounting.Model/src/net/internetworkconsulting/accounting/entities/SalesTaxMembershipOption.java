/*
 * Copyright (C) 2016 Internetwork Consulting LLC
 *
 * This program is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by the Free 
 * Software Foundation, version 3 of the License.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for 
 * more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see http://www.gnu.org/licenses/.
 */
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
