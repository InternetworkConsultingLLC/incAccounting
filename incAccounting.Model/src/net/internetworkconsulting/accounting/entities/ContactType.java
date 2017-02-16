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
import net.internetworkconsulting.accounting.data.ContactTypesRow;
import net.internetworkconsulting.bootstrap.entities.Option;
import net.internetworkconsulting.bootstrap.entities.User;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.Row;
import net.internetworkconsulting.data.mysql.Statement;

public class ContactType extends ContactTypesRow {
	private static List<Option> lstOptions;
	public static String TYPE_BUSINESS_GUID = "4134430d7bb64a97b95f7862bea83644";
	public static String TYPE_EMPLOYEE_GUID = "7c85de861e784b9f8dcb4a456e267869";
	public static List<Option> loadOptions(AdapterInterface adapter, boolean force) throws Exception {
		if(lstOptions != null && !force)
			return lstOptions;
		
		Statement stmt = new Statement(adapter.getSession().readFile("sql/ContactType.loadOptions.sql"));		
		List<Option> lst = adapter.load(Option.class, stmt);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		lstOptions = lst;		
		return lst;
	}
	public static List loadSearch(AdapterInterface adapter, List<String> columns, String search) throws Exception { return Row.loadSearch(adapter, ContactType.class, columns, search); }
	public static List getSearchColumns() {
		LinkedList<String> lstColumns = new LinkedList<>();
		lstColumns.add(ContactType.DISPLAY_NAME);
		lstColumns.add(ContactType.IS_ALLOWED);
		return lstColumns;
	}
	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
		this.setIsAllowed(true);
	}
}