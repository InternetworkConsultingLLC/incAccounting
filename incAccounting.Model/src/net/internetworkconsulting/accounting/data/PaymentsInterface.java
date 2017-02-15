/*
 * incGenerator - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface PaymentsInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setPaymentTypesGuid(java.lang.String value) throws Exception;
	java.lang.String getPaymentTypesGuid();
	
	boolean setBankDepositsGuid(java.lang.String value) throws Exception;
	java.lang.String getBankDepositsGuid();
	
	boolean setOurNumber(java.lang.String value) throws Exception;
	java.lang.String getOurNumber();
	
	boolean setDate(java.sql.Date value) throws Exception;
	java.sql.Date getDate();
	
	boolean setPostedAccountsGuid(java.lang.String value) throws Exception;
	java.lang.String getPostedAccountsGuid();
	
	boolean setPostedTransactionsGuid(java.lang.String value) throws Exception;
	java.lang.String getPostedTransactionsGuid();
	
	boolean setPrepaymentDocumentsGuid(java.lang.String value) throws Exception;
	java.lang.String getPrepaymentDocumentsGuid();
	
	boolean setExpenseLinesDocumentsGuid(java.lang.String value) throws Exception;
	java.lang.String getExpenseLinesDocumentsGuid();
	
	boolean setContactsGuid(java.lang.String value) throws Exception;
	java.lang.String getContactsGuid();
	
	boolean setContactsDisplayName(java.lang.String value) throws Exception;
	java.lang.String getContactsDisplayName();
	
	boolean setTheirNumber(java.lang.String value) throws Exception;
	java.lang.String getTheirNumber();
	
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
	
	boolean setTotal(java.math.BigDecimal value) throws Exception;
	java.math.BigDecimal getTotal();
	
	
	<T extends PaymentApplicationsRow> List<T> loadPaymentApplications(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	
	<T extends PaymentTypesRow> T loadPaymentType(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends BankDepositsRow> T loadBankDeposits(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends AccountsRow> T loadAccount(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends TransactionsRow> T loadTransaction(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends ContactsRow> T loadContact(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends ContactsRow> T loadBillingContact(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
