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
package net.internetworkconsulting.bootstrap.entities;

import java.util.LinkedList;
import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.Row;
import net.internetworkconsulting.data.mysql.Statement;

public class Setting extends net.internetworkconsulting.bootstrap.data.SettingsRow {
	private static final long serialVersionUID = 1L;

	public static String TYPE_BOOLEAN = "Boolean";
	public static String TYPE_GUID = "Guid";
	public static String TYPE_NUMBER = "Number";
	public static String TYPE_STRING = "String";

	public Setting() { super(); }
	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
	}

	public static List loadSearch(AdapterInterface adapter, List<String> columns, String search) throws Exception { return Row.loadSearch(adapter, Setting.class, columns, search); }
	public static List<String> getSearchColumns() {
		LinkedList<String> lstColumns = new LinkedList<>();
		lstColumns.add(net.internetworkconsulting.bootstrap.entities.Setting.KEY);
		lstColumns.add(net.internetworkconsulting.bootstrap.entities.User.SQL_USER);
//		lstColumns.add(net.internetworkconsulting.bootstrap.entities.Setting.TYPE);
//		lstColumns.add(net.internetworkconsulting.bootstrap.entities.Setting.VALUE);
		return lstColumns;
	}
	
	public static <T extends Setting> T loadByKey(AdapterInterface adapter, Class model, String value) throws Exception {
		String sql = "SELECT * FROM \"Settings\" WHERE \"" + Setting.KEY + "\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique settings row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);
	}

}
