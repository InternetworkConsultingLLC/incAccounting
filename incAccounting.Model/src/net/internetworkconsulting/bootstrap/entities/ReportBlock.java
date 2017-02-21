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

import java.util.HashMap;
import java.util.List;
import net.internetworkconsulting.bootstrap.data.ReportBlocksRow;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.Row;
import net.internetworkconsulting.data.mysql.Statement;
import net.internetworkconsulting.template.Template;

public class ReportBlock extends ReportBlocksRow {
	private static List<Option> lstOptions;
	public static List<Option> loadOptions(AdapterInterface adapter, boolean force) throws Exception {
		if(lstOptions != null && !force)
			return lstOptions;

		Statement stmt = new Statement(adapter.getSession().readJar(ReportBlock.class, "ReportBlock.loadOptions.sql"));		
		List<Option> lst = adapter.load(Option.class, stmt);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		lstOptions = lst;
		return lst;
	}

	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
	}

	private Object lstChildrenChildren = null;
	public <T extends ReportBlocksRow> List<T> loadChildren(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstChildrenChildren == null || force) {
			String sql = "SELECT * FROM \"%s\" WHERE \"%s\"={PRIMARYKEY} ORDER BY \"%s\"";
			sql = String.format(sql, ReportBlock.TABLE_NAME, ReportBlock.PARENT_BLOCK_GUID, ReportBlock.PRIORITY);
			Statement stmt = new Statement(sql);
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstChildrenChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstChildrenChildren;
	}
	void generate(AdapterInterface adapter, Template document, HashMap<String, String> values) throws Exception {
		// create query and execute
		Statement stmt = new Statement(getSqlQuery());
		for(String key: values.keySet())
			stmt.getParameters().put("{" + key + "}", values.get(key));
		List<Row> lstRows = adapter.load(Row.class, stmt);
		
		// for each row
		for(Row row: lstRows) {
			// load isolated hash map
			HashMap<String, String> hmIsolated = new HashMap<>();
			hmIsolated.putAll(values);
			for(String column: row.getColumns().keySet())
				hmIsolated.put(column, Statement.convertObjectToString(row.get(column), null));
			
			// handle children
			List<ReportBlock> lstChildren = loadChildren(adapter, ReportBlock.class, true);
			for(ReportBlock child: lstChildren)
				child.generate(adapter, document, hmIsolated);
			
			// output this row
			for(String key: hmIsolated.keySet())
				document.set(key, hmIsolated.get(key));

			document.touch(getName());
			document.parse(getName());
		}				
	}
}
