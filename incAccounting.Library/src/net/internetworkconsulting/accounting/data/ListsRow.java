package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class ListsRow extends Row implements ListsInterface {
	public ListsRow() { 
		super(); 
		setSqlTableName("lists");
		setSqlSecurableGuid("e6dbca21d4865b265ca2780205b19305");
	}
	public static String TABLE_NAME = "lists";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String DISPLAY_NAME = "Display Name";
	public boolean setDisplayName(java.lang.String value) throws Exception { return set(DISPLAY_NAME, value); }
	public java.lang.String getDisplayName() { return (java.lang.String) get(DISPLAY_NAME); }
	
	public static String SQL_QUERY = "SQL Query";
	public boolean setSqlQuery(java.lang.String value) throws Exception { return set(SQL_QUERY, value); }
	public java.lang.String getSqlQuery() { return (java.lang.String) get(SQL_QUERY); }
	

	// child loaders
	
	protected Object lstFiltersChildren = null;
	public <T extends ListFiltersRow> List<T> loadFilters(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstFiltersChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"List Filters\" WHERE \"Lists GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstFiltersChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstFiltersChildren;
	}
	

	// parent loaders
	
	protected Object rSecurableParent = null;
	public <T extends SecurablesRow> T loadSecurable(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rSecurableParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Securables\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique Securables row by GUID (" + Statement.convertObjectToString(this.getGuid(), null) + ")!");
			rSecurableParent = lst.get(0);
		}
		return (T) rSecurableParent;
	}
	

	// unique key loaders
	
	public static <T extends ListsRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"Lists\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique Lists row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
	public static <T extends ListsRow> T loadByDisplayName(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"Lists\" WHERE \"Display Name\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique Lists row by 'Display Name': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
}
