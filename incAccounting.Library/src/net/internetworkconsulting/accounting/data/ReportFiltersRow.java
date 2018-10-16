package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class ReportFiltersRow extends Row implements ReportFiltersInterface {
	public ReportFiltersRow() { 
		super(); 
		setSqlTableName("report filters");
		setSqlSecurableGuid("6501bdcc5e1343b4977011e53664d16c");
	}
	public static String TABLE_NAME = "report filters";

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
	
	public static String PRIORITY = "Priority";
	public boolean setPriority(java.lang.Long value) throws Exception { return set(PRIORITY, value); }
	public java.lang.Long getPriority() { return (java.lang.Long) get(PRIORITY); }
	

	// child loaders
	

	// parent loaders
	
	protected Object rReportParent = null;
	public <T extends ReportsRow> T loadReport(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rReportParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"reports\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getReportsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique reports row by GUID (" + Statement.convertObjectToString(this.getReportsGuid(), null) + ")!");
			rReportParent = lst.get(0);
		}
		return (T) rReportParent;
	}
	

	// unique key loaders
	
	public static <T extends ReportFiltersRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"report filters\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique report filters row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
}
