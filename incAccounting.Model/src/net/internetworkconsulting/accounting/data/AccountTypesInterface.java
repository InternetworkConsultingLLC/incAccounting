/*
 * incGenerator - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface AccountTypesInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setName(java.lang.String value) throws Exception;
	java.lang.String getName();
	
	boolean setGetsClosed(java.lang.Boolean value) throws Exception;
	java.lang.Boolean getGetsClosed();
	
	
	<T extends AccountsRow> List<T> loadAccounts(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	
}
