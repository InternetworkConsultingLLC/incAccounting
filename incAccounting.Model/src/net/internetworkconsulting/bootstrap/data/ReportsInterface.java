/*
 * incGenerator - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.bootstrap.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface ReportsInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setDisplayName(java.lang.String value) throws Exception;
	java.lang.String getDisplayName();
	
	boolean setHtmlTemplate(java.lang.String value) throws Exception;
	java.lang.String getHtmlTemplate();
	
	
	<T extends ReportBlocksRow> List<T> loadBlocks(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends ReportFiltersRow> List<T> loadFilters(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	
	<T extends SecurablesRow> T loadSecurable(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
