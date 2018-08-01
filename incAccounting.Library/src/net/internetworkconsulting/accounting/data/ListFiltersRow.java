package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class ListFiltersRow extends Row implements ListFiltersInterface {
	public ListFiltersRow() { 
		super(); 
		setSqlTableName("list filters");
		setSqlSecurableGuid("f40716e94de76648bb16432757b3d334");
	}
	public static String TABLE_NAME = "list filters";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String LISTS_GUID = "Lists GUID";
	public boolean setListsGuid(java.lang.String value) throws Exception { return set(LISTS_GUID, value); }
	public java.lang.String getListsGuid() { return (java.lang.String) get(LISTS_GUID); }
	
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
	
	protected Object rListParent = null;
	public <T extends ListsRow> T loadList(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rListParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Lists\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getListsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique Lists row by GUID (" + Statement.convertObjectToString(this.getListsGuid(), null) + ")!");
			rListParent = lst.get(0);
		}
		return (T) rListParent;
	}
	

	// unique key loaders
	
	public static <T extends ListFiltersRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"List Filters\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique List Filters row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
}
