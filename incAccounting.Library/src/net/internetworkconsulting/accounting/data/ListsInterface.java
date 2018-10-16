package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface ListsInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setDisplayName(java.lang.String value) throws Exception;
	java.lang.String getDisplayName();
	
	boolean setSqlQuery(java.lang.String value) throws Exception;
	java.lang.String getSqlQuery();
	
	boolean setEditUrl(java.lang.String value) throws Exception;
	java.lang.String getEditUrl();
	
	boolean setAddUrl(java.lang.String value) throws Exception;
	java.lang.String getAddUrl();
	
	
	<T extends ListFiltersRow> List<T> loadFilters(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	
	<T extends SecurablesRow> T loadSecurable(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
