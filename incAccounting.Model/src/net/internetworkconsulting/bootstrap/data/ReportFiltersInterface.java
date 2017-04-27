package net.internetworkconsulting.bootstrap.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface ReportFiltersInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setReportsGuid(java.lang.String value) throws Exception;
	java.lang.String getReportsGuid();
	
	boolean setPrompt(java.lang.String value) throws Exception;
	java.lang.String getPrompt();
	
	boolean setDataType(java.lang.String value) throws Exception;
	java.lang.String getDataType();
	
	boolean setQuery(java.lang.String value) throws Exception;
	java.lang.String getQuery();
	
	boolean setPriority(java.lang.Long value) throws Exception;
	java.lang.Long getPriority();
	
	
	
	<T extends ReportsRow> T loadReport(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
