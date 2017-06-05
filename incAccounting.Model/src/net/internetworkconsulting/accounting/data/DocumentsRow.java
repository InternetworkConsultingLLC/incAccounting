/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class DocumentsRow extends Row implements DocumentsInterface {
	public DocumentsRow() { 
		super(); 
		setSqlTableName("documents");
		setSqlSecurableGuid("21f64da1e5792c8295b964d159a14491");
	}
	public static String TABLE_NAME = "documents";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String DOCUMENT_TYPES_GUID = "Document Types GUID";
	public boolean setDocumentTypesGuid(java.lang.String value) throws Exception { return set(DOCUMENT_TYPES_GUID, value); }
	public java.lang.String getDocumentTypesGuid() { return (java.lang.String) get(DOCUMENT_TYPES_GUID); }
	
	public static String PARENT_DOCUMENTS_GUID = "Parent Documents GUID";
	public boolean setParentDocumentsGuid(java.lang.String value) throws Exception { return set(PARENT_DOCUMENTS_GUID, value); }
	public java.lang.String getParentDocumentsGuid() { return (java.lang.String) get(PARENT_DOCUMENTS_GUID); }
	
	public static String REFERENCE_NUMBER = "Reference Number";
	public boolean setReferenceNumber(java.lang.String value) throws Exception { return set(REFERENCE_NUMBER, value); }
	public java.lang.String getReferenceNumber() { return (java.lang.String) get(REFERENCE_NUMBER); }
	
	public static String DATE = "Date";
	public boolean setDate(java.sql.Date value) throws Exception { return set(DATE, value); }
	public java.sql.Date getDate() { return (java.sql.Date) get(DATE); }
	
	public static String DUE_DATE = "Due Date";
	public boolean setDueDate(java.sql.Date value) throws Exception { return set(DUE_DATE, value); }
	public java.sql.Date getDueDate() { return (java.sql.Date) get(DUE_DATE); }
	
	public static String POSTED_ACCOUNTS_GUID = "Posted Accounts GUID";
	public boolean setPostedAccountsGuid(java.lang.String value) throws Exception { return set(POSTED_ACCOUNTS_GUID, value); }
	public java.lang.String getPostedAccountsGuid() { return (java.lang.String) get(POSTED_ACCOUNTS_GUID); }
	
	public static String POSTED_TRANSACTIONS_GUID = "Posted Transactions GUID";
	public boolean setPostedTransactionsGuid(java.lang.String value) throws Exception { return set(POSTED_TRANSACTIONS_GUID, value); }
	public java.lang.String getPostedTransactionsGuid() { return (java.lang.String) get(POSTED_TRANSACTIONS_GUID); }
	
	public static String CONTACTS_GUID = "Contacts GUID";
	public boolean setContactsGuid(java.lang.String value) throws Exception { return set(CONTACTS_GUID, value); }
	public java.lang.String getContactsGuid() { return (java.lang.String) get(CONTACTS_GUID); }
	
	public static String CONTACTS_DISPLAY_NAME = "Contacts Display Name";
	public boolean setContactsDisplayName(java.lang.String value) throws Exception { return set(CONTACTS_DISPLAY_NAME, value); }
	public java.lang.String getContactsDisplayName() { return (java.lang.String) get(CONTACTS_DISPLAY_NAME); }
	
	public static String CUSTOMER_REFERENCE = "Customer Reference";
	public boolean setCustomerReference(java.lang.String value) throws Exception { return set(CUSTOMER_REFERENCE, value); }
	public java.lang.String getCustomerReference() { return (java.lang.String) get(CUSTOMER_REFERENCE); }
	
	public static String TERMS = "Terms";
	public boolean setTerms(java.lang.String value) throws Exception { return set(TERMS, value); }
	public java.lang.String getTerms() { return (java.lang.String) get(TERMS); }
	
	public static String SHIPPING_METHOD = "Shipping Method";
	public boolean setShippingMethod(java.lang.String value) throws Exception { return set(SHIPPING_METHOD, value); }
	public java.lang.String getShippingMethod() { return (java.lang.String) get(SHIPPING_METHOD); }
	
	public static String SHIPPING_NUMBER = "Shipping Number";
	public boolean setShippingNumber(java.lang.String value) throws Exception { return set(SHIPPING_NUMBER, value); }
	public java.lang.String getShippingNumber() { return (java.lang.String) get(SHIPPING_NUMBER); }
	
	public static String BILLING_CONTACTS_GUID = "Billing Contacts GUID";
	public boolean setBillingContactsGuid(java.lang.String value) throws Exception { return set(BILLING_CONTACTS_GUID, value); }
	public java.lang.String getBillingContactsGuid() { return (java.lang.String) get(BILLING_CONTACTS_GUID); }
	
	public static String BILLING_ADDRESS_DISPLAY_NAME = "Billing Address Display Name";
	public boolean setBillingAddressDisplayName(java.lang.String value) throws Exception { return set(BILLING_ADDRESS_DISPLAY_NAME, value); }
	public java.lang.String getBillingAddressDisplayName() { return (java.lang.String) get(BILLING_ADDRESS_DISPLAY_NAME); }
	
	public static String BILLING_ADDRESS_LINE_1 = "Billing Address Line 1";
	public boolean setBillingAddressLine1(java.lang.String value) throws Exception { return set(BILLING_ADDRESS_LINE_1, value); }
	public java.lang.String getBillingAddressLine1() { return (java.lang.String) get(BILLING_ADDRESS_LINE_1); }
	
	public static String BILLING_ADDRESS_LINE_2 = "Billing Address Line 2";
	public boolean setBillingAddressLine2(java.lang.String value) throws Exception { return set(BILLING_ADDRESS_LINE_2, value); }
	public java.lang.String getBillingAddressLine2() { return (java.lang.String) get(BILLING_ADDRESS_LINE_2); }
	
	public static String BILLING_ADDRESS_CITY = "Billing Address City";
	public boolean setBillingAddressCity(java.lang.String value) throws Exception { return set(BILLING_ADDRESS_CITY, value); }
	public java.lang.String getBillingAddressCity() { return (java.lang.String) get(BILLING_ADDRESS_CITY); }
	
	public static String BILLING_ADDRESS_STATE = "Billing Address State";
	public boolean setBillingAddressState(java.lang.String value) throws Exception { return set(BILLING_ADDRESS_STATE, value); }
	public java.lang.String getBillingAddressState() { return (java.lang.String) get(BILLING_ADDRESS_STATE); }
	
	public static String BILLING_ADDRESS_POSTAL_CODE = "Billing Address Postal Code";
	public boolean setBillingAddressPostalCode(java.lang.String value) throws Exception { return set(BILLING_ADDRESS_POSTAL_CODE, value); }
	public java.lang.String getBillingAddressPostalCode() { return (java.lang.String) get(BILLING_ADDRESS_POSTAL_CODE); }
	
	public static String BILLING_ADDRESS_COUNTRY = "Billing Address Country";
	public boolean setBillingAddressCountry(java.lang.String value) throws Exception { return set(BILLING_ADDRESS_COUNTRY, value); }
	public java.lang.String getBillingAddressCountry() { return (java.lang.String) get(BILLING_ADDRESS_COUNTRY); }
	
	public static String SHIPPING_CONTACTS_GUID = "Shipping Contacts GUID";
	public boolean setShippingContactsGuid(java.lang.String value) throws Exception { return set(SHIPPING_CONTACTS_GUID, value); }
	public java.lang.String getShippingContactsGuid() { return (java.lang.String) get(SHIPPING_CONTACTS_GUID); }
	
	public static String SHIPPING_ADDRESS_DISPLAY_NAME = "Shipping Address Display Name";
	public boolean setShippingAddressDisplayName(java.lang.String value) throws Exception { return set(SHIPPING_ADDRESS_DISPLAY_NAME, value); }
	public java.lang.String getShippingAddressDisplayName() { return (java.lang.String) get(SHIPPING_ADDRESS_DISPLAY_NAME); }
	
	public static String SHIPPING_ADDRESS_LINE_1 = "Shipping Address Line 1";
	public boolean setShippingAddressLine1(java.lang.String value) throws Exception { return set(SHIPPING_ADDRESS_LINE_1, value); }
	public java.lang.String getShippingAddressLine1() { return (java.lang.String) get(SHIPPING_ADDRESS_LINE_1); }
	
	public static String SHIPPING_ADDRESS_LINE_2 = "Shipping Address Line 2";
	public boolean setShippingAddressLine2(java.lang.String value) throws Exception { return set(SHIPPING_ADDRESS_LINE_2, value); }
	public java.lang.String getShippingAddressLine2() { return (java.lang.String) get(SHIPPING_ADDRESS_LINE_2); }
	
	public static String SHIPPING_ADDRESS_CITY = "Shipping Address City";
	public boolean setShippingAddressCity(java.lang.String value) throws Exception { return set(SHIPPING_ADDRESS_CITY, value); }
	public java.lang.String getShippingAddressCity() { return (java.lang.String) get(SHIPPING_ADDRESS_CITY); }
	
	public static String SHIPPING_ADDRESS_STATE = "Shipping Address State";
	public boolean setShippingAddressState(java.lang.String value) throws Exception { return set(SHIPPING_ADDRESS_STATE, value); }
	public java.lang.String getShippingAddressState() { return (java.lang.String) get(SHIPPING_ADDRESS_STATE); }
	
	public static String SHIPPING_ADDRESS_POSTAL_CODE = "Shipping Address Postal Code";
	public boolean setShippingAddressPostalCode(java.lang.String value) throws Exception { return set(SHIPPING_ADDRESS_POSTAL_CODE, value); }
	public java.lang.String getShippingAddressPostalCode() { return (java.lang.String) get(SHIPPING_ADDRESS_POSTAL_CODE); }
	
	public static String SHIPPING_ADDRESS_COUNTRY = "Shipping Address Country";
	public boolean setShippingAddressCountry(java.lang.String value) throws Exception { return set(SHIPPING_ADDRESS_COUNTRY, value); }
	public java.lang.String getShippingAddressCountry() { return (java.lang.String) get(SHIPPING_ADDRESS_COUNTRY); }
	
	public static String SALES_TAXES_GUID = "Sales Taxes GUID";
	public boolean setSalesTaxesGuid(java.lang.String value) throws Exception { return set(SALES_TAXES_GUID, value); }
	public java.lang.String getSalesTaxesGuid() { return (java.lang.String) get(SALES_TAXES_GUID); }
	
	public static String TAXABLE_SUBTOTAL = "Taxable Subtotal";
	public boolean setTaxableSubtotal(java.math.BigDecimal value) throws Exception { return set(TAXABLE_SUBTOTAL, value); }
	public java.math.BigDecimal getTaxableSubtotal() { return (java.math.BigDecimal) get(TAXABLE_SUBTOTAL); }
	
	public static String NONTAXABLE_SUBTOTAL = "Nontaxable Subtotal";
	public boolean setNontaxableSubtotal(java.math.BigDecimal value) throws Exception { return set(NONTAXABLE_SUBTOTAL, value); }
	public java.math.BigDecimal getNontaxableSubtotal() { return (java.math.BigDecimal) get(NONTAXABLE_SUBTOTAL); }
	
	public static String TAX_RATE = "Tax Rate";
	public boolean setTaxRate(java.math.BigDecimal value) throws Exception { return set(TAX_RATE, value); }
	public java.math.BigDecimal getTaxRate() { return (java.math.BigDecimal) get(TAX_RATE); }
	
	public static String SUBTOTAL = "Subtotal";
	public boolean setSubtotal(java.math.BigDecimal value) throws Exception { return set(SUBTOTAL, value); }
	public java.math.BigDecimal getSubtotal() { return (java.math.BigDecimal) get(SUBTOTAL); }
	
	public static String TAXES = "Taxes";
	public boolean setTaxes(java.math.BigDecimal value) throws Exception { return set(TAXES, value); }
	public java.math.BigDecimal getTaxes() { return (java.math.BigDecimal) get(TAXES); }
	
	public static String TOTAL = "Total";
	public boolean setTotal(java.math.BigDecimal value) throws Exception { return set(TOTAL, value); }
	public java.math.BigDecimal getTotal() { return (java.math.BigDecimal) get(TOTAL); }
	

	// child loaders
	
	protected Object lstDocumentLinesChildren = null;
	public <T extends DocumentLinesRow> List<T> loadDocumentLines(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstDocumentLinesChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"document lines\" WHERE \"Documents GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstDocumentLinesChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstDocumentLinesChildren;
	}
	
	protected Object lstChildDocumentsChildren = null;
	public <T extends DocumentsRow> List<T> loadChildDocuments(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstChildDocumentsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"documents\" WHERE \"Parent Documents GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstChildDocumentsChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstChildDocumentsChildren;
	}
	
	protected Object lstPaymentApplicationsChildren = null;
	public <T extends PaymentApplicationsRow> List<T> loadPaymentApplications(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstPaymentApplicationsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"payment applications\" WHERE \"Documents GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstPaymentApplicationsChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstPaymentApplicationsChildren;
	}
	
	protected Object lstTimeEntriesChildren = null;
	public <T extends TimeEntriesRow> List<T> loadTimeEntries(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstTimeEntriesChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"time entries\" WHERE \"Documents GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstTimeEntriesChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstTimeEntriesChildren;
	}
	

	// parent loaders
	
	protected Object rDocumentTypeParent = null;
	public <T extends DocumentTypesRow> T loadDocumentType(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rDocumentTypeParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"document types\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getDocumentTypesGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique document types row by GUID (" + Statement.convertObjectToString(this.getDocumentTypesGuid(), null) + ")!");
			rDocumentTypeParent = lst.get(0);
		}
		return (T) rDocumentTypeParent;
	}
	
	protected Object rParentDocumentParent = null;
	public <T extends DocumentsRow> T loadParentDocument(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rParentDocumentParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"documents\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getParentDocumentsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique documents row by GUID (" + Statement.convertObjectToString(this.getParentDocumentsGuid(), null) + ")!");
			rParentDocumentParent = lst.get(0);
		}
		return (T) rParentDocumentParent;
	}
	
	protected Object rAccountParent = null;
	public <T extends AccountsRow> T loadAccount(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rAccountParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"accounts\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getPostedAccountsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique accounts row by GUID (" + Statement.convertObjectToString(this.getPostedAccountsGuid(), null) + ")!");
			rAccountParent = lst.get(0);
		}
		return (T) rAccountParent;
	}
	
	protected Object rTransactionParent = null;
	public <T extends TransactionsRow> T loadTransaction(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rTransactionParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"transactions\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getPostedTransactionsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique transactions row by GUID (" + Statement.convertObjectToString(this.getPostedTransactionsGuid(), null) + ")!");
			rTransactionParent = lst.get(0);
		}
		return (T) rTransactionParent;
	}
	
	protected Object rContactParent = null;
	public <T extends ContactsRow> T loadContact(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rContactParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"contacts\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getContactsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique contacts row by GUID (" + Statement.convertObjectToString(this.getContactsGuid(), null) + ")!");
			rContactParent = lst.get(0);
		}
		return (T) rContactParent;
	}
	
	protected Object rBillingContactParent = null;
	public <T extends ContactsRow> T loadBillingContact(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rBillingContactParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"contacts\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getBillingContactsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique contacts row by GUID (" + Statement.convertObjectToString(this.getBillingContactsGuid(), null) + ")!");
			rBillingContactParent = lst.get(0);
		}
		return (T) rBillingContactParent;
	}
	
	protected Object rShippingContactParent = null;
	public <T extends ContactsRow> T loadShippingContact(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rShippingContactParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"contacts\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getShippingContactsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique contacts row by GUID (" + Statement.convertObjectToString(this.getShippingContactsGuid(), null) + ")!");
			rShippingContactParent = lst.get(0);
		}
		return (T) rShippingContactParent;
	}
	
	protected Object rSalesTaxParent = null;
	public <T extends SalesTaxesRow> T loadSalesTax(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rSalesTaxParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"sales taxes\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getSalesTaxesGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique sales taxes row by GUID (" + Statement.convertObjectToString(this.getSalesTaxesGuid(), null) + ")!");
			rSalesTaxParent = lst.get(0);
		}
		return (T) rSalesTaxParent;
	}
	

	// unique key loaders
	
	public static <T extends DocumentsRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"documents\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique documents row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
}
