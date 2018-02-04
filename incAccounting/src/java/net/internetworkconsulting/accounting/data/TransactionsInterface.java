package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface TransactionsInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setDate(java.util.Date value) throws Exception;
	java.util.Date getDate();
	
	boolean setReferenceNumber(java.lang.String value) throws Exception;
	java.lang.String getReferenceNumber();
	
	boolean setTransactionTypesGuid(java.lang.String value) throws Exception;
	java.lang.String getTransactionTypesGuid();
	
	
	<T extends BankDepositsRow> List<T> loadBankDeposit(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends DocumentsRow> List<T> loadDocument(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends PaymentsRow> List<T> loadPayment(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends PayrollChecksRow> List<T> loadPayrollChecks(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends TransactionLinesRow> List<T> loadTransactionLines(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	
	<T extends TransactionTypesRow> T loadTransactionType(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
