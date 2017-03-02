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
import java.util.LinkedList;
import java.util.List;
import net.internetworkconsulting.bootstrap.data.ReportBlocksRow;
import net.internetworkconsulting.bootstrap.data.ReportsRow;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.Row;
import net.internetworkconsulting.data.mysql.Statement;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public class Report extends ReportsRow {
	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
	}

	public static List<String> getSearchColumns() {
		LinkedList<String> lstColumns = new LinkedList<>();
		lstColumns.add(net.internetworkconsulting.bootstrap.entities.Report.DISPLAY_NAME);
		return lstColumns;
	}
	public static List loadSearch(AdapterInterface adapter, List<String> columns, String search) throws Exception {
		return Row.loadSearch(adapter, Report.class, columns, search);
	}

	private static List<Option> lstOptions;
	public static List<Option> loadOptions(AdapterInterface adapter, boolean force) throws Exception {
		if(lstOptions != null && !force)
			return lstOptions;

		Statement stmt = new Statement(adapter.getSession().readJar(Report.class, "Report.loadOptions.sql"));		
		List<Option> lst = adapter.load(Option.class, stmt);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		lstOptions = lst;
		return lst;
	}
	
	private Object lstBlocksChildren = null;
	public <T extends ReportBlocksRow> List<T> loadBlocks(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstBlocksChildren == null || force) {
			String sql = "SELECT * FROM \"%s\" WHERE \"%s\"={PRIMARYKEY} ORDER BY \"%s\"";
			sql = String.format(sql, ReportBlock.TABLE_NAME, ReportBlock.REPORTS_GUID, ReportBlock.PRIORITY);
			Statement stmt = new Statement(sql);
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstBlocksChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstBlocksChildren;
	}

	public void beforeSave(AdapterInterface adapter) throws Exception {
		Securable sec = null;
		
		if(getRowState() == RowState.Insert) {
			sec = new Securable();
			sec.setGuid(this.getGuid());
		} else if(getRowState() == RowState.Update) {
			sec = this.loadSecurable(adapter, Securable.class, false);
		}		

		if(sec != null) {
			sec.setDisplayName("Report - " + this.getDisplayName());
			adapter.save(Securable.TABLE_NAME, sec);			
		}	
	}
	public void afterSave(AdapterInterface adapter) throws Exception {
		if(getRowState() == RowState.Delete) {
			Securable sec = this.loadSecurable(adapter, Securable.class, false);
			sec.setIsDeleted(true);
			adapter.save(Securable.TABLE_NAME, sec);			
		}		
	}

	public String generate(AdapterInterface adapter) throws Exception {
		Template document = new Template(getHtmlTemplate(), new HtmlSyntax());
		
		List<ReportFilter> lstFilters = loadFilters(adapter, ReportFilter.class, false);
		HashMap<String, String> hmValues = new HashMap<>();
		for(ReportFilter rf: lstFilters)
			hmValues.put(rf.getPrompt(), rf.getValue());
		
		List<ReportBlock> lstBlocks = loadBlocks(adapter, ReportBlock.class, true);
		for(ReportBlock rb: lstBlocks) {
			HashMap<String, String> hmIsolated = new HashMap<>();
			hmIsolated.putAll(hmValues);
			rb.generate(adapter, document, hmIsolated);
		}
		
		return document.generate();
	}

	public Report handleCopy(AdapterInterface adapter) throws Exception {
		Report objNew = new Report();
		objNew.initialize();
		for(String key : this.getOriginals().keySet())
			objNew.getChanges().put(key, this.getOriginals().get(key));
		
		objNew.setGuid(User.newGuid());
		objNew.setDisplayName(objNew.getDisplayName() + " " + objNew.getGuid());
		
		List<ReportFilter> lstOldFilters = this.loadFilters(adapter, ReportFilter.class, false);
		List<ReportFilter> lstNewFilters = objNew.loadFilters(adapter, ReportFilter.class, false);
		for(ReportFilter filter : lstOldFilters)
			lstNewFilters.add(filter.handleCopy(adapter));
			
		List<ReportBlock> lstOldBlocks = this.loadBlocks(adapter, ReportBlock.class, false);
		List<ReportBlock> lstNewBlocks = objNew.loadBlocks(adapter, ReportBlock.class, false);
		for(ReportBlock block : lstOldBlocks)
			lstNewFilters.add(block.handleCopy(adapter));
	
		return objNew;
	}

}
