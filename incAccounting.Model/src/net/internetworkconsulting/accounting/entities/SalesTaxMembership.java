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

import java.util.List;
import net.internetworkconsulting.accounting.data.SalesTaxMembershipsRow;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.mysql.Statement;

public class SalesTaxMembership extends SalesTaxMembershipsRow {
	public static SalesTaxMembership loadByParentAndChild(AdapterInterface adapter, String parentGuid, String childGuid) throws Exception {
		String sql = "SELECT * FROM \"Sales Tax Memberships\" WHERE \"Parent Sales Taxes GUID\" =  {Parent GUID} AND \"Child Sales Taxes GUID\" = {Child GUID}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{Parent GUID}", parentGuid);
		stmt.getParameters().put("{Child GUID}", childGuid);
		List<SalesTaxMembership> lstMemberships = adapter.load(SalesTaxMembership.class, stmt, true);
		
		if(lstMemberships.size() != 1)
			throw new Exception("Could not locate a membership!");
		
		return lstMemberships.get(0);
	}
 }
