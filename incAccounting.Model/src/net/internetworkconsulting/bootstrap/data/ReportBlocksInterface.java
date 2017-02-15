/*
 * incGenerator - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.bootstrap.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface ReportBlocksInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setReportsGuid(java.lang.String value) throws Exception;
	java.lang.String getReportsGuid();
	
	boolean setParentBlockGuid(java.lang.String value) throws Exception;
	java.lang.String getParentBlockGuid();
	
	boolean setPriority(java.lang.Integer value) throws Exception;
	java.lang.Integer getPriority();
	
	boolean setName(java.lang.String value) throws Exception;
	java.lang.String getName();
	
	boolean setSqlQuery(java.lang.String value) throws Exception;
	java.lang.String getSqlQuery();
	
	
	<T extends ReportBlocksRow> List<T> loadChildren(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	
	<T extends ReportsRow> T loadReport(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends ReportBlocksRow> T loadParent(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
