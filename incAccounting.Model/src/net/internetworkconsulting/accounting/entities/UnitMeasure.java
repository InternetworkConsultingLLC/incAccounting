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

import java.util.LinkedList;
import java.util.List;
import net.internetworkconsulting.accounting.data.UnitMeasuresRow;
import net.internetworkconsulting.bootstrap.entities.Option;
import net.internetworkconsulting.bootstrap.entities.User;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.Row;
import net.internetworkconsulting.data.mysql.Statement;

public class UnitMeasure extends UnitMeasuresRow {
	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
		this.setIsAllowed(true);		
	}
	
	private static List<Option> lstOptions;
	public static List<Option> loadOptions(AdapterInterface adapter, boolean force) throws Exception {
		if(lstOptions != null && !force)
			return lstOptions;
		
		Statement stmt = new Statement(adapter.getSession().readFile("sql/UnitMeasure.loadOptions.sql"));		
		List<Option> lst = adapter.load(Option.class, stmt);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		lstOptions = lst;		
		return lst;
	}
	public static List loadSearch(AdapterInterface adapter, List<String> columns, String search) throws Exception { return Row.loadSearch(adapter, UnitMeasure.class, columns, search); }
	public static List getSearchColumns() {
		LinkedList<String> lstColumns = new LinkedList<>();
		lstColumns.add(UnitMeasure.DISPLAY_NAME);
		lstColumns.add(UnitMeasure.ABBREVIATION);
		lstColumns.add(UnitMeasure.IS_ALLOWED);
		return lstColumns;
	}

//	private HashMap<String, BigDecimal> hmConversions = null;
//	public HashMap<String, BigDecimal> loadConversionMap(AdapterInterface adapter) throws Exception {
//		if(hmConversions != null)
//			return hmConversions;
//		
//		hmConversions = new HashMap<>();
//		
//		List<UnitConversion> lstConversions = this.loadBaseToQuantityConversions(adapter, UnitConversion.class, true);
//		for(UnitConversion uc : lstConversions) {
//			if(hmConversions.containsKey(uc.getQuantityUnitMeasuresGuid()))
//				continue;
//			
//			hmConversions.put(uc.getQuantityUnitMeasuresGuid(), uc.getQuantity());			
//		}
//
//		return hmConversions;
//	}
}
