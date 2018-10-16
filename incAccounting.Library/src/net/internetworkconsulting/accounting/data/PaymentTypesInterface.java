package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface PaymentTypesInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setAccountsGuid(java.lang.String value) throws Exception;
	java.lang.String getAccountsGuid();
	
	boolean setIsSalesRelated(java.lang.Boolean value) throws Exception;
	java.lang.Boolean getIsSalesRelated();
	
	
	<T extends PaymentTypesDocumentTypesRow> List<T> loadPaymentTypesDocumentTypes(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends PaymentsRow> List<T> loadPayments(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	
	<T extends TransactionTypesRow> T loadTransactionType(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends AccountsRow> T loadAccount(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
