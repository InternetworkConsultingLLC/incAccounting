/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.bootstrap.data;

import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class ReportsRow extends Row implements ReportsInterface {
	public ReportsRow() { 
		super(); 
		setSqlTableName("Reports");
		setSqlSecurableGuid("c91c7b93c28cd18741b71f727ee81ee3");
	}
	public static String TABLE_NAME = "Reports";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String DISPLAY_NAME = "Display Name";
	public boolean setDisplayName(java.lang.String value) throws Exception { return set(DISPLAY_NAME, value); }
	public java.lang.String getDisplayName() { return (java.lang.String) get(DISPLAY_NAME); }
	
	public static String HTML_TEMPLATE = "HTML Template";
	public boolean setHtmlTemplate(java.lang.String value) throws Exception { return set(HTML_TEMPLATE, value); }
	public java.lang.String getHtmlTemplate() { return (java.lang.String) get(HTML_TEMPLATE); }
	

	// child loaders
	
	protected Object lstBlocksChildren = null;
	public <T extends ReportBlocksRow> List<T> loadBlocks(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstBlocksChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Report Blocks\" WHERE \"Reports GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstBlocksChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstBlocksChildren;
	}
	
	protected Object lstFiltersChildren = null;
	public <T extends ReportFiltersRow> List<T> loadFilters(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstFiltersChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Report Filters\" WHERE \"Reports GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstFiltersChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstFiltersChildren;
	}
	

	// parent loaders
	
	protected Object rSecurableParent = null;
	public <T extends SecurablesRow> T loadSecurable(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rSecurableParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Securables\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			List<T> lst = adapter.load(model, stmt);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique Securables row by GUID (" + Statement.convertObjectToString(this.getGuid(), null) + ")!");
			rSecurableParent = lst.get(0);
		}
		return (T) rSecurableParent;
	}
	

	// unique key loaders
	
	public static <T extends ReportsRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"Reports\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique Reports row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
	public static <T extends ReportsRow> T loadByDisplayName(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"Reports\" WHERE \"Display Name\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique Reports row by 'Display Name': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	

	// load all
	public static <T extends ReportsRow> List<T> loadAll(AdapterInterface adapter, Class model) throws Exception {
		Statement stmt = new Statement("SELECT * FROM \"Report Filters\"");
		return (List<T>) adapter.load(model, stmt);
	}
}
