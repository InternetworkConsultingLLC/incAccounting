/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.bootstrap.data ;

import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class SecurablesRow extends Row implements SecurablesInterface {
	public SecurablesRow() { 
		super(); 
		setSqlTableName("Securables");
		setSqlSecurableGuid("b748fae7af491847c7a3fcb4db6e13b1");
	}
	public static String TABLE_NAME = "Securables";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String DISPLAY_NAME = "Display Name";
	public boolean setDisplayName(java.lang.String value) throws Exception { return set(DISPLAY_NAME, value); }
	public java.lang.String getDisplayName() { return (java.lang.String) get(DISPLAY_NAME); }
	

	// child loaders
	
	protected Object lstPermissionsChildren = null;
	public <T extends PermissionsRow> List<T> loadPermissions(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstPermissionsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Permissions\" WHERE \"Securables GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstPermissionsChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstPermissionsChildren;
	}
	
	protected Object lstReportChildren = null;
	public <T extends ReportsRow> List<T> loadReport(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstReportChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Reports\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstReportChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstReportChildren;
	}
	

	// parent loaders
	

	// unique key loaders
	
	public static <T extends SecurablesRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"Securables\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique Securables row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
	public static <T extends SecurablesRow> T loadByDisplayName(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"Securables\" WHERE \"Display Name\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique Securables row by 'Display Name': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	

	// load all
	public static <T extends SecurablesRow> List<T> loadAll(AdapterInterface adapter, Class model) throws Exception {
		Statement stmt = new Statement("SELECT * FROM \"Reports\"");
		return (List<T>) adapter.load(model, stmt);
	}
}
