package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface AccountsInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setAccountTypesGuid(java.lang.String value) throws Exception;
	java.lang.String getAccountTypesGuid();
	
	boolean setParentAccountsGuid(java.lang.String value) throws Exception;
	java.lang.String getParentAccountsGuid();
	
	boolean setSegment(java.lang.String value) throws Exception;
	java.lang.String getSegment();
	
	boolean setNumber(java.lang.String value) throws Exception;
	java.lang.String getNumber();
	
	boolean setName(java.lang.String value) throws Exception;
	java.lang.String getName();
	
	boolean setNestedName(java.lang.String value) throws Exception;
	java.lang.String getNestedName();
	
	boolean setIsAllowed(java.lang.Boolean value) throws Exception;
	java.lang.Boolean getIsAllowed();
	
	
	<T extends AccountsRow> List<T> loadChildren(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends DocumentLinesRow> List<T> loadDocumentLines(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends DocumentTypesRow> List<T> loadDocumentTypes(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends DocumentsRow> List<T> loadDocuments(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends ItemsRow> List<T> loadInventoryItems(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends ItemsRow> List<T> loadPurchaseItems(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends ItemsRow> List<T> loadSalesItems(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends PaymentTypesRow> List<T> loadPaymentTypes(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends PaymentsRow> List<T> loadPayments(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends PayrollChecksRow> List<T> loadPayrollChecks(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends PayrollFieldsRow> List<T> loadCreditPayrollFields(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends PayrollFieldsRow> List<T> loadDebitPayrollFields(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends ReconciliationsRow> List<T> loadReconciliations(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends SalesTaxesRow> List<T> loadSalesTaxes(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends TransactionLinesRow> List<T> loadTransactionLines(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	
	<T extends AccountTypesRow> T loadAccountType(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends AccountsRow> T loadParentAccount(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
