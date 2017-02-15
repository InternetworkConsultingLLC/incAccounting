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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import net.internetworkconsulting.accounting.data.PayrollFieldsRow;
import net.internetworkconsulting.bootstrap.entities.Option;
import net.internetworkconsulting.bootstrap.entities.User;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.Row;
import net.internetworkconsulting.data.mysql.Statement;

public class PayrollField extends PayrollFieldsRow {
	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
	}
	
	
	private static List<Option> lstOptions;
	public static List<Option> loadOptions(AdapterInterface adapter, boolean force) throws Exception {
		if(lstOptions != null && !force)
			return lstOptions;
		
		Statement stmt = new Statement(adapter.getSession().readFile("sql/PayrollField.loadOptions.sql"));		
		List<Option> lst = adapter.load(Option.class, stmt);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		lstOptions = lst;
		return lst;
	}
	private static HashMap<String, List<Option>> hmOptionsByType = new HashMap<>();
	public static List<Option> loadOptionsByType(AdapterInterface adapter, boolean force, String type_guid) throws Exception {
		if(hmOptionsByType.containsKey(type_guid) && !force)
			return hmOptionsByType.get(type_guid);
		
		Statement stmt = new Statement(adapter.getSession().readFile("sql/PayrollField.loadOptionsByType.sql"));		
		stmt.getParameters().put("{Type GUID}", type_guid);
		List<Option> lst = adapter.load(Option.class, stmt);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);

		hmOptionsByType.put(type_guid, lst);
		return hmOptionsByType.get(type_guid);
	}
	public static List loadSearch(AdapterInterface adapter, List<String> columns, String search) throws Exception { return Row.loadSearch(adapter, Document.class, columns, search); }
	public static List getSearchColumns() {
		LinkedList<String> lstColumns = new LinkedList<>();
		lstColumns.add(PayrollField.NAME);
		lstColumns.add(PayrollField.PAYROLL_FIELD_TYPES_GUID);
		return lstColumns;
	}
}
