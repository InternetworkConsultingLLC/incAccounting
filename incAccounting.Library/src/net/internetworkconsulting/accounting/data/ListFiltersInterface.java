package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface ListFiltersInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setListsGuid(java.lang.String value) throws Exception;
	java.lang.String getListsGuid();
	
	boolean setPrompt(java.lang.String value) throws Exception;
	java.lang.String getPrompt();
	
	boolean setDataType(java.lang.String value) throws Exception;
	java.lang.String getDataType();
	
	boolean setQuery(java.lang.String value) throws Exception;
	java.lang.String getQuery();
	
	
	
	<T extends ListsRow> T loadList(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
