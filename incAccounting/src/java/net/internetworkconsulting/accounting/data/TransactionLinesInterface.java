package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface TransactionLinesInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setTransactionsGuid(java.lang.String value) throws Exception;
	java.lang.String getTransactionsGuid();
	
	boolean setSortOrder(java.lang.Integer value) throws Exception;
	java.lang.Integer getSortOrder();
	
	boolean setDescription(java.lang.String value) throws Exception;
	java.lang.String getDescription();
	
	boolean setDebit(java.math.BigDecimal value) throws Exception;
	java.math.BigDecimal getDebit();
	
	boolean setJobsGuid(java.lang.String value) throws Exception;
	java.lang.String getJobsGuid();
	
	boolean setDepartmentsGuid(java.lang.String value) throws Exception;
	java.lang.String getDepartmentsGuid();
	
	boolean setAccountsGuid(java.lang.String value) throws Exception;
	java.lang.String getAccountsGuid();
	
	boolean setReconciliationsGuid(java.lang.String value) throws Exception;
	java.lang.String getReconciliationsGuid();
	
	
	<T extends RegisterEntriesRow> List<T> loadRegisterEntries(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	
	<T extends TransactionsRow> T loadTransaction(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends JobsRow> T loadJob(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends DepartmentsRow> T loadDepartment(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends AccountsRow> T loadAccount(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends ReconciliationsRow> T loadReconciliation(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
