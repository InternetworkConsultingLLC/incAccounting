/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.bootstrap.data;

import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class ReportFiltersRow extends Row implements ReportFiltersInterface {
	public ReportFiltersRow() { 
		super(); 
		setSqlTableName("Report Filters");
		setSqlSecurableGuid("755a0e7f295f45dacc4af7776f5150f3");
	}
	public static String TABLE_NAME = "Report Filters";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String REPORTS_GUID = "Reports GUID";
	public boolean setReportsGuid(java.lang.String value) throws Exception { return set(REPORTS_GUID, value); }
	public java.lang.String getReportsGuid() { return (java.lang.String) get(REPORTS_GUID); }
	
	public static String PROMPT = "Prompt";
	public boolean setPrompt(java.lang.String value) throws Exception { return set(PROMPT, value); }
	public java.lang.String getPrompt() { return (java.lang.String) get(PROMPT); }
	
	public static String DATA_TYPE = "Data Type";
	public boolean setDataType(java.lang.String value) throws Exception { return set(DATA_TYPE, value); }
	public java.lang.String getDataType() { return (java.lang.String) get(DATA_TYPE); }
	
	public static String QUERY = "Query";
	public boolean setQuery(java.lang.String value) throws Exception { return set(QUERY, value); }
	public java.lang.String getQuery() { return (java.lang.String) get(QUERY); }
	

	// child loaders
	

	// parent loaders
	
	protected Object rReportParent = null;
	public <T extends ReportsRow> T loadReport(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rReportParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Reports\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getReportsGuid());
			List<T> lst = adapter.load(model, stmt);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique Reports row by GUID (" + Statement.convertObjectToString(this.getReportsGuid(), null) + ")!");
			rReportParent = lst.get(0);
		}
		return (T) rReportParent;
	}
	

	// unique key loaders
	
	public static <T extends ReportFiltersRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"Report Filters\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique Report Filters row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	

	// load all
	public static <T extends ReportFiltersRow> List<T> loadAll(AdapterInterface adapter, Class model) throws Exception {
		Statement stmt = new Statement("SELECT * FROM \"Report Filters\"");
		return (List<T>) adapter.load(model, stmt);
	}
}
