package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface DocumentTypesInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setIsSalesRelated(java.lang.Boolean value) throws Exception;
	java.lang.Boolean getIsSalesRelated();
	
	boolean setIsCreditMemo(java.lang.Boolean value) throws Exception;
	java.lang.Boolean getIsCreditMemo();
	
	boolean setIsPosting(java.lang.Boolean value) throws Exception;
	java.lang.Boolean getIsPosting();
	
	boolean setAccountsGuid(java.lang.String value) throws Exception;
	java.lang.String getAccountsGuid();
	
	
	<T extends DocumentsRow> List<T> loadDocuments(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends PaymentTypesDocumentTypesRow> List<T> loadPaymentTypesDocumentTypes(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	
	<T extends TransactionTypesRow> T loadTransactionType(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends AccountsRow> T loadAccount(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
