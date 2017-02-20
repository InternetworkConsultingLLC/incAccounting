package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface DocumentsInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setDocumentTypesGuid(java.lang.String value) throws Exception;
	java.lang.String getDocumentTypesGuid();
	
	boolean setParentDocumentsGuid(java.lang.String value) throws Exception;
	java.lang.String getParentDocumentsGuid();
	
	boolean setReferenceNumber(java.lang.String value) throws Exception;
	java.lang.String getReferenceNumber();
	
	boolean setDate(java.sql.Date value) throws Exception;
	java.sql.Date getDate();
	
	boolean setDueDate(java.sql.Date value) throws Exception;
	java.sql.Date getDueDate();
	
	boolean setPostedAccountsGuid(java.lang.String value) throws Exception;
	java.lang.String getPostedAccountsGuid();
	
	boolean setPostedTransactionsGuid(java.lang.String value) throws Exception;
	java.lang.String getPostedTransactionsGuid();
	
	boolean setContactsGuid(java.lang.String value) throws Exception;
	java.lang.String getContactsGuid();
	
	boolean setContactsDisplayName(java.lang.String value) throws Exception;
	java.lang.String getContactsDisplayName();
	
	boolean setCustomerReference(java.lang.String value) throws Exception;
	java.lang.String getCustomerReference();
	
	boolean setTerms(java.lang.String value) throws Exception;
	java.lang.String getTerms();
	
	boolean setShippingMethod(java.lang.String value) throws Exception;
	java.lang.String getShippingMethod();
	
	boolean setShippingNumber(java.lang.String value) throws Exception;
	java.lang.String getShippingNumber();
	
	boolean setBillingContactsGuid(java.lang.String value) throws Exception;
	java.lang.String getBillingContactsGuid();
	
	boolean setBillingAddressDisplayName(java.lang.String value) throws Exception;
	java.lang.String getBillingAddressDisplayName();
	
	boolean setBillingAddressLine1(java.lang.String value) throws Exception;
	java.lang.String getBillingAddressLine1();
	
	boolean setBillingAddressLine2(java.lang.String value) throws Exception;
	java.lang.String getBillingAddressLine2();
	
	boolean setBillingAddressCity(java.lang.String value) throws Exception;
	java.lang.String getBillingAddressCity();
	
	boolean setBillingAddressState(java.lang.String value) throws Exception;
	java.lang.String getBillingAddressState();
	
	boolean setBillingAddressPostalCode(java.lang.String value) throws Exception;
	java.lang.String getBillingAddressPostalCode();
	
	boolean setBillingAddressCountry(java.lang.String value) throws Exception;
	java.lang.String getBillingAddressCountry();
	
	boolean setShippingContactsGuid(java.lang.String value) throws Exception;
	java.lang.String getShippingContactsGuid();
	
	boolean setShippingAddressDisplayName(java.lang.String value) throws Exception;
	java.lang.String getShippingAddressDisplayName();
	
	boolean setShippingAddressLine1(java.lang.String value) throws Exception;
	java.lang.String getShippingAddressLine1();
	
	boolean setShippingAddressLine2(java.lang.String value) throws Exception;
	java.lang.String getShippingAddressLine2();
	
	boolean setShippingAddressCity(java.lang.String value) throws Exception;
	java.lang.String getShippingAddressCity();
	
	boolean setShippingAddressState(java.lang.String value) throws Exception;
	java.lang.String getShippingAddressState();
	
	boolean setShippingAddressPostalCode(java.lang.String value) throws Exception;
	java.lang.String getShippingAddressPostalCode();
	
	boolean setShippingAddressCountry(java.lang.String value) throws Exception;
	java.lang.String getShippingAddressCountry();
	
	boolean setSalesTaxesGuid(java.lang.String value) throws Exception;
	java.lang.String getSalesTaxesGuid();
	
	boolean setTaxableSubtotal(java.math.BigDecimal value) throws Exception;
	java.math.BigDecimal getTaxableSubtotal();
	
	boolean setNontaxableSubtotal(java.math.BigDecimal value) throws Exception;
	java.math.BigDecimal getNontaxableSubtotal();
	
	boolean setTaxRate(java.math.BigDecimal value) throws Exception;
	java.math.BigDecimal getTaxRate();
	
	boolean setSubtotal(java.math.BigDecimal value) throws Exception;
	java.math.BigDecimal getSubtotal();
	
	boolean setTaxes(java.math.BigDecimal value) throws Exception;
	java.math.BigDecimal getTaxes();
	
	boolean setTotal(java.math.BigDecimal value) throws Exception;
	java.math.BigDecimal getTotal();
	
	
	<T extends DocumentLinesRow> List<T> loadDocumentLines(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends DocumentsRow> List<T> loadChildDocuments(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends PaymentApplicationsRow> List<T> loadPaymentApplications(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	
	<T extends DocumentTypesRow> T loadDocumentType(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends DocumentsRow> T loadParentDocument(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends AccountsRow> T loadAccount(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends TransactionsRow> T loadTransaction(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends ContactsRow> T loadContact(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends ContactsRow> T loadBillingContact(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends ContactsRow> T loadShippingContact(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends SalesTaxesRow> T loadSalesTax(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
