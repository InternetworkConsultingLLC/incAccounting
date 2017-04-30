/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class ReportBlocksRow extends Row implements ReportBlocksInterface {
	public ReportBlocksRow() { 
		super(); 
		setSqlTableName("report blocks");
		setSqlSecurableGuid("3884f17163a34ab5fdfb98575065d852");
	}
	public static String TABLE_NAME = "report blocks";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String REPORTS_GUID = "Reports GUID";
	public boolean setReportsGuid(java.lang.String value) throws Exception { return set(REPORTS_GUID, value); }
	public java.lang.String getReportsGuid() { return (java.lang.String) get(REPORTS_GUID); }
	
	public static String PARENT_BLOCK_GUID = "Parent Block GUID";
	public boolean setParentBlockGuid(java.lang.String value) throws Exception { return set(PARENT_BLOCK_GUID, value); }
	public java.lang.String getParentBlockGuid() { return (java.lang.String) get(PARENT_BLOCK_GUID); }
	
	public static String PRIORITY = "Priority";
	public boolean setPriority(java.lang.Integer value) throws Exception { return set(PRIORITY, value); }
	public java.lang.Integer getPriority() { return (java.lang.Integer) get(PRIORITY); }
	
	public static String NAME = "Name";
	public boolean setName(java.lang.String value) throws Exception { return set(NAME, value); }
	public java.lang.String getName() { return (java.lang.String) get(NAME); }
	
	public static String SQL_QUERY = "SQL Query";
	public boolean setSqlQuery(java.lang.String value) throws Exception { return set(SQL_QUERY, value); }
	public java.lang.String getSqlQuery() { return (java.lang.String) get(SQL_QUERY); }
	

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
	
	protected Object rParentParent = null;
	public <T extends ReportBlocksRow> T loadParent(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rParentParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"report blocks\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getParentBlockGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique report blocks row by GUID (" + Statement.convertObjectToString(this.getParentBlockGuid(), null) + ")!");
			rParentParent = lst.get(0);
		}
		return (T) rParentParent;
	}
	

	// unique key loaders
	
	public static <T extends ReportBlocksRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"Report Blocks\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique Report Blocks row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
}
