/*
 * incGenerator - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface UnitMeasuresInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setDisplayName(java.lang.String value) throws Exception;
	java.lang.String getDisplayName();
	
	boolean setAbbreviation(java.lang.String value) throws Exception;
	java.lang.String getAbbreviation();
	
	boolean setIsAllowed(java.lang.Boolean value) throws Exception;
	java.lang.Boolean getIsAllowed();
	
	
	<T extends ConversionsRow> List<T> loadLeftConversions(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends ConversionsRow> List<T> loadRightConversions(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends DocumentLinesRow> List<T> loadDocumentLines(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends ItemsRow> List<T> loadItems(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	
}
