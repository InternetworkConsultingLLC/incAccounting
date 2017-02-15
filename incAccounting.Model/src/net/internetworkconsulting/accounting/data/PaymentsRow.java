/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import net.internetworkconsulting.data.mysql.Statement;
import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;

public class PaymentsRow extends Row implements PaymentsInterface {
	public PaymentsRow() { 
		super(); 
		setSqlTableName("payments");
		setSqlSecurableGuid("84d5eaf713c96eecb3d2c4a83e64dc9a");
	}
	public static String TABLE_NAME = "payments";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String PAYMENT_TYPES_GUID = "Payment Types GUID";
	public boolean setPaymentTypesGuid(java.lang.String value) throws Exception { return set(PAYMENT_TYPES_GUID, value); }
	public java.lang.String getPaymentTypesGuid() { return (java.lang.String) get(PAYMENT_TYPES_GUID); }
	
	public static String BANK_DEPOSITS_GUID = "Bank Deposits GUID";
	public boolean setBankDepositsGuid(java.lang.String value) throws Exception { return set(BANK_DEPOSITS_GUID, value); }
	public java.lang.String getBankDepositsGuid() { return (java.lang.String) get(BANK_DEPOSITS_GUID); }
	
	public static String OUR_NUMBER = "Our Number";
	public boolean setOurNumber(java.lang.String value) throws Exception { return set(OUR_NUMBER, value); }
	public java.lang.String getOurNumber() { return (java.lang.String) get(OUR_NUMBER); }
	
	public static String DATE = "Date";
	public boolean setDate(java.sql.Date value) throws Exception { return set(DATE, value); }
	public java.sql.Date getDate() { return (java.sql.Date) get(DATE); }
	
	public static String POSTED_ACCOUNTS_GUID = "Posted Accounts GUID";
	public boolean setPostedAccountsGuid(java.lang.String value) throws Exception { return set(POSTED_ACCOUNTS_GUID, value); }
	public java.lang.String getPostedAccountsGuid() { return (java.lang.String) get(POSTED_ACCOUNTS_GUID); }
	
	public static String POSTED_TRANSACTIONS_GUID = "Posted Transactions GUID";
	public boolean setPostedTransactionsGuid(java.lang.String value) throws Exception { return set(POSTED_TRANSACTIONS_GUID, value); }
	public java.lang.String getPostedTransactionsGuid() { return (java.lang.String) get(POSTED_TRANSACTIONS_GUID); }
	
	public static String PREPAYMENT_DOCUMENTS_GUID = "Prepayment Documents GUID";
	public boolean setPrepaymentDocumentsGuid(java.lang.String value) throws Exception { return set(PREPAYMENT_DOCUMENTS_GUID, value); }
	public java.lang.String getPrepaymentDocumentsGuid() { return (java.lang.String) get(PREPAYMENT_DOCUMENTS_GUID); }
	
	public static String EXPENSE_LINES_DOCUMENTS_GUID = "Expense Lines Documents GUID";
	public boolean setExpenseLinesDocumentsGuid(java.lang.String value) throws Exception { return set(EXPENSE_LINES_DOCUMENTS_GUID, value); }
	public java.lang.String getExpenseLinesDocumentsGuid() { return (java.lang.String) get(EXPENSE_LINES_DOCUMENTS_GUID); }
	
	public static String CONTACTS_GUID = "Contacts GUID";
	public boolean setContactsGuid(java.lang.String value) throws Exception { return set(CONTACTS_GUID, value); }
	public java.lang.String getContactsGuid() { return (java.lang.String) get(CONTACTS_GUID); }
	
	public static String CONTACTS_DISPLAY_NAME = "Contacts Display Name";
	public boolean setContactsDisplayName(java.lang.String value) throws Exception { return set(CONTACTS_DISPLAY_NAME, value); }
	public java.lang.String getContactsDisplayName() { return (java.lang.String) get(CONTACTS_DISPLAY_NAME); }
	
	public static String THEIR_NUMBER = "Their Number";
	public boolean setTheirNumber(java.lang.String value) throws Exception { return set(THEIR_NUMBER, value); }
	public java.lang.String getTheirNumber() { return (java.lang.String) get(THEIR_NUMBER); }
	
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
	
	public static String TOTAL = "Total";
	public boolean setTotal(java.math.BigDecimal value) throws Exception { return set(TOTAL, value); }
	public java.math.BigDecimal getTotal() { return (java.math.BigDecimal) get(TOTAL); }
	

	// child loaders
	
	protected Object lstPaymentApplicationsChildren = null;
	public <T extends PaymentApplicationsRow> List<T> loadPaymentApplications(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstPaymentApplicationsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"payment applications\" WHERE \"Payments GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstPaymentApplicationsChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstPaymentApplicationsChildren;
	}
	

	// parent loaders
	
	protected Object rPaymentTypeParent = null;
	public <T extends PaymentTypesRow> T loadPaymentType(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rPaymentTypeParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"payment types\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getPaymentTypesGuid());
			List<T> lst = adapter.load(model, stmt);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique payment types row by GUID (" + Statement.convertObjectToString(this.getPaymentTypesGuid(), null) + ")!");
			rPaymentTypeParent = lst.get(0);
		}
		return (T) rPaymentTypeParent;
	}
	
	protected Object rBankDepositsParent = null;
	public <T extends BankDepositsRow> T loadBankDeposits(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rBankDepositsParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"bank deposits\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getBankDepositsGuid());
			List<T> lst = adapter.load(model, stmt);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique bank deposits row by GUID (" + Statement.convertObjectToString(this.getBankDepositsGuid(), null) + ")!");
			rBankDepositsParent = lst.get(0);
		}
		return (T) rBankDepositsParent;
	}
	
	protected Object rAccountParent = null;
	public <T extends AccountsRow> T loadAccount(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rAccountParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"accounts\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getPostedAccountsGuid());
			List<T> lst = adapter.load(model, stmt);
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
			List<T> lst = adapter.load(model, stmt);
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
			List<T> lst = adapter.load(model, stmt);
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
			List<T> lst = adapter.load(model, stmt);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique contacts row by GUID (" + Statement.convertObjectToString(this.getBillingContactsGuid(), null) + ")!");
			rBillingContactParent = lst.get(0);
		}
		return (T) rBillingContactParent;
	}
	

	// unique key loaders
	
	public static <T extends PaymentsRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"payments\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique payments row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
	public static <T extends PaymentsRow> T loadByOurNumber(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"payments\" WHERE \"Our Number\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique payments row by 'Our Number': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
	public static <T extends PaymentsRow> T loadByPostedTransactionsGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"payments\" WHERE \"Posted Transactions GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique payments row by 'Posted Transactions GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	

	// load all
	public static <T extends PaymentsRow> List<T> loadAll(AdapterInterface adapter, Class model) throws Exception {
		Statement stmt = new Statement("SELECT * FROM \"payments\"");
		return (List<T>) adapter.load(model, stmt);
	}
}
