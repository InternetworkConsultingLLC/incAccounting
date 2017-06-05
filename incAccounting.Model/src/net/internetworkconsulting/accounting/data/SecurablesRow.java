/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class SecurablesRow extends Row implements SecurablesInterface {
	public SecurablesRow() { 
		super(); 
		setSqlTableName("securables");
		setSqlSecurableGuid("681b75b096e94bd4e7bbbc0bfb389955");
	}
	public static String TABLE_NAME = "securables";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String DISPLAY_NAME = "Display Name";
	public boolean setDisplayName(java.lang.String value) throws Exception { return set(DISPLAY_NAME, value); }
	public java.lang.String getDisplayName() { return (java.lang.String) get(DISPLAY_NAME); }
	

	// child loaders
	
	protected Object lstReportChildren = null;
	public <T extends ReportsRow> List<T> loadReport(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstReportChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Reports\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstReportChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstReportChildren;
	}
	
	protected Object lstPermissionsChildren = null;
	public <T extends PermissionsRow> List<T> loadPermissions(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstPermissionsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"permissions\" WHERE \"Securables GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstPermissionsChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstPermissionsChildren;
	}
	

	// parent loaders
	

	// unique key loaders
	
	public static <T extends SecurablesRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"securables\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique securables row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
	public static <T extends SecurablesRow> T loadByDisplayName(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"securables\" WHERE \"Display Name\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique securables row by 'Display Name': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
}
