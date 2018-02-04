package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface BankDepositsInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setAccountsGuid(java.lang.String value) throws Exception;
	java.lang.String getAccountsGuid();
	
	boolean setDate(java.util.Date value) throws Exception;
	java.util.Date getDate();
	
	boolean setNumber(java.lang.String value) throws Exception;
	java.lang.String getNumber();
	
	boolean setItems(java.lang.Integer value) throws Exception;
	java.lang.Integer getItems();
	
	boolean setTotal(java.math.BigDecimal value) throws Exception;
	java.math.BigDecimal getTotal();
	
	boolean setPostedTransactionsGuid(java.lang.String value) throws Exception;
	java.lang.String getPostedTransactionsGuid();
	
	
	<T extends PaymentsRow> List<T> loadPayments(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	
	<T extends TransactionsRow> T loadTransaction(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
