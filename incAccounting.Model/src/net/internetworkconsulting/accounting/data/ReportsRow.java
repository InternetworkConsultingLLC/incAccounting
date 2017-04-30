/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class ReportsRow extends Row implements ReportsInterface {
	public ReportsRow() { 
		super(); 
		setSqlTableName("reports");
		setSqlSecurableGuid("a8445719836f2d5e8b51986410e14728");
	}
	public static String TABLE_NAME = "reports";

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
	
	public static String TITLE = "Title";
	public boolean setTitle(java.lang.String value) throws Exception { return set(TITLE, value); }
	public java.lang.String getTitle() { return (java.lang.String) get(TITLE); }
	
	public static String AUTO_LOAD = "Auto Load";
	public boolean setAutoLoad(java.lang.Boolean value) throws Exception { return set(AUTO_LOAD, value); }
	public java.lang.Boolean getAutoLoad() { return (java.lang.Boolean) get(AUTO_LOAD); }
	

	// child loaders
	
	protected Object lstBlocksChildren = null;
	public <T extends ReportBlocksRow> List<T> loadBlocks(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstBlocksChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Report Blocks\" WHERE \"Reports GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstBlocksChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstBlocksChildren;
	}
	
	protected Object lstFiltersChildren = null;
	public <T extends ReportFiltersRow> List<T> loadFilters(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstFiltersChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"report filters\" WHERE \"Reports GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstFiltersChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstFiltersChildren;
	}
	

	// parent loaders
	
	protected Object rSecurableParent = null;
	public <T extends SecurablesRow> T loadSecurable(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rSecurableParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"securables\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique securables row by GUID (" + Statement.convertObjectToString(this.getGuid(), null) + ")!");
			rSecurableParent = lst.get(0);
		}
		return (T) rSecurableParent;
	}
	

	// unique key loaders
	
	public static <T extends ReportsRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"reports\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique reports row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
	public static <T extends ReportsRow> T loadByDisplayName(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"reports\" WHERE \"Display Name\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique reports row by 'Display Name': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
}
