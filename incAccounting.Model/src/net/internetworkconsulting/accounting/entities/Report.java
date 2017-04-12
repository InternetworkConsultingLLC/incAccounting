package net.internetworkconsulting.accounting.entities;

import java.util.HashMap;
import java.util.List;
import net.internetworkconsulting.accounting.data.ReportBlocksRow;
import net.internetworkconsulting.accounting.data.ReportFiltersRow;
import net.internetworkconsulting.accounting.data.ReportsRow;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.mysql.Statement;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public class Report extends ReportsRow {
	public static String SPLASH_GUID = "18afbfdf422246b1a549f34dad94f8b7";
	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
	}

	private static List<Option> lstOptions;
	public static List<Option> loadOptions(AdapterInterface adapter, boolean force) throws Exception {
		if(lstOptions != null && !force)
			return lstOptions;

		Statement stmt = new Statement(adapter.getSession().readJar(Report.class, "Report.loadOptions.sql"));		
		List<Option> lst = adapter.load(Option.class, stmt, true);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		lstOptions = lst;
		return lst;
	}

	public static Report loadByDisplayName(AdapterInterface adapter, String display_name) throws Exception {
		String sql = "SELECT * FROM \"%s\" WHERE \"%s\"={Name}";
		sql = String.format(sql, Report.TABLE_NAME, Report.DISPLAY_NAME);

		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{Name}", display_name);
		List<Report> lst = adapter.load(Report.class, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate report by Display Name '" + display_name + "'!");
		
		return lst.get(0);
	}
	
	public <T extends ReportBlocksRow> List<T> loadBlocks(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstBlocksChildren == null || force) {
			String sql = "SELECT * FROM \"%s\" WHERE \"%s\"={PRIMARYKEY} ORDER BY \"%s\"";
			sql = String.format(sql, ReportBlock.TABLE_NAME, ReportBlock.REPORTS_GUID, ReportBlock.PRIORITY);
			Statement stmt = new Statement(sql);
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstBlocksChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstBlocksChildren;
	}
	public <T extends ReportFiltersRow> List<T> loadFilters(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstFiltersChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Report Filters\" WHERE \"Reports GUID\"={PRIMARYKEY} ORDER BY \"Priority\"");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstFiltersChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstFiltersChildren;
	}

	public void beforeSave(AdapterInterface adapter) throws Exception {
		Securable sec = null;
		
		if(Report.SPLASH_GUID.equals(getGuid()) && RowState.Delete == getRowState())
			throw new Exception("You cannot delete the splash report!");
		
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
		Template document;
		try { document = new Template(getHtmlTemplate(), new HtmlSyntax()); }
		catch(Exception ex) {
			throw new Exception("Problem parsing HTML template.  If a 'no match' error, your BEGIN block does not have an END.  Please check that opening and closing blocks and brackets match.", ex);
		}
		
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
		for(ReportFilter filter : lstOldFilters) {
			ReportFilter newFilter = filter.handleCopy(adapter);
			newFilter.setReportsGuid(objNew.getGuid());
			lstNewFilters.add(newFilter);
		}
			
		List<ReportBlock> lstOldBlocks = this.loadBlocks(adapter, ReportBlock.class, false);
		List<ReportBlock> lstNewBlocks = objNew.loadBlocks(adapter, ReportBlock.class, false);
		for(ReportBlock block : lstOldBlocks) {
			ReportBlock newBlock = block.handleCopy(adapter);
			newBlock.setReportsGuid(objNew.getGuid());
			lstNewBlocks.add(newBlock);
		}
	
		return objNew;
	}
	public void handleSave(AdapterInterface adapter) throws Exception {
		adapter.begin(false);
		
		try {
			adapter.save(Report.TABLE_NAME, this);

			List<ReportBlock> lstBlocks = this.loadBlocks(adapter, ReportBlock.class, false);
			for(ReportBlock block : lstBlocks)
				block.handleSave(adapter);

			adapter.save(ReportFilter.TABLE_NAME, this.loadFilters(adapter, ReportFilter.class, false));			
		} catch(Exception ex) {
			adapter.rollback(false);
			throw ex;
		}

		adapter.commit(false);
	}
	
}
