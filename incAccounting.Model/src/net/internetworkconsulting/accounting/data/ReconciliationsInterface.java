/*
 * incGenerator - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface ReconciliationsInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setAccountsGuid(java.lang.String value) throws Exception;
	java.lang.String getAccountsGuid();
	
	boolean setDate(java.sql.Date value) throws Exception;
	java.sql.Date getDate();
	
	boolean setStatementEndingBalance(java.math.BigDecimal value) throws Exception;
	java.math.BigDecimal getStatementEndingBalance();
	
	boolean setOffBy(java.math.BigDecimal value) throws Exception;
	java.math.BigDecimal getOffBy();
	
	
	<T extends TransactionLinesRow> List<T> loadTransactionLines(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	
	<T extends AccountsRow> T loadAccount(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
