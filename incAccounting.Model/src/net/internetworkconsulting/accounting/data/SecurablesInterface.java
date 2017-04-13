package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface SecurablesInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setDisplayName(java.lang.String value) throws Exception;
	java.lang.String getDisplayName();
	
	
	<T extends PermissionsRow> List<T> loadPermissions(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends ReportsRow> List<T> loadReport(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	
}
