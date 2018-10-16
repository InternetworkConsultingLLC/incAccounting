package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface ContactsInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setParentContactsGuid(java.lang.String value) throws Exception;
	java.lang.String getParentContactsGuid();
	
	boolean setDisplayName(java.lang.String value) throws Exception;
	java.lang.String getDisplayName();
	
	boolean setIsAllowed(java.lang.Boolean value) throws Exception;
	java.lang.Boolean getIsAllowed();
	
	boolean setContactSince(java.util.Date value) throws Exception;
	java.util.Date getContactSince();
	
	boolean setDefaultShippingContactsGuid(java.lang.String value) throws Exception;
	java.lang.String getDefaultShippingContactsGuid();
	
	boolean setWebsite(java.lang.String value) throws Exception;
	java.lang.String getWebsite();
	
	boolean setEmailAddress(java.lang.String value) throws Exception;
	java.lang.String getEmailAddress();
	
	boolean setOfficePhone(java.lang.String value) throws Exception;
	java.lang.String getOfficePhone();
	
	boolean setMobilePhone(java.lang.String value) throws Exception;
	java.lang.String getMobilePhone();
	
	boolean setHomePhone(java.lang.String value) throws Exception;
	java.lang.String getHomePhone();
	
	boolean setFaxNumber(java.lang.String value) throws Exception;
	java.lang.String getFaxNumber();
	
	boolean setLine1(java.lang.String value) throws Exception;
	java.lang.String getLine1();
	
	boolean setLine2(java.lang.String value) throws Exception;
	java.lang.String getLine2();
	
	boolean setCountry(java.lang.String value) throws Exception;
	java.lang.String getCountry();
	
	boolean setCity(java.lang.String value) throws Exception;
	java.lang.String getCity();
	
	boolean setState(java.lang.String value) throws Exception;
	java.lang.String getState();
	
	boolean setPostalCode(java.lang.String value) throws Exception;
	java.lang.String getPostalCode();
	
	boolean setTaxGroupGuid(java.lang.String value) throws Exception;
	java.lang.String getTaxGroupGuid();
	
	boolean setContactTypesGuid(java.lang.String value) throws Exception;
	java.lang.String getContactTypesGuid();
	
	
	<T extends ContactNotesRow> List<T> loadContactNotes(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends ContactsRow> List<T> loadShippingParentContact(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends ContactsRow> List<T> loadChildrenContacts(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends DocumentsRow> List<T> loadBillingDocuments(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends DocumentsRow> List<T> loadDocuments(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends DocumentsRow> List<T> loadShippingDocuments(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends EmployeesRow> List<T> loadEmployee(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends EmployeesRow> List<T> loadSubordinates(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends ItemsRow> List<T> loadItems(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends JobsRow> List<T> loadJobs(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends PaymentsRow> List<T> loadBillingPayments(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends PaymentsRow> List<T> loadContactPayments(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends RegisterEntriesRow> List<T> loadRegisterEntries(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends SalesTaxesRow> List<T> loadSalesTaxes(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends TimeEntriesRow> List<T> loadTimeEntries(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends TimeSheetsRow> List<T> loadTimeSheets(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	
	<T extends ContactsRow> T loadParentContact(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends ContactsRow> T loadDefaultShippingContact(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends ContactTypesRow> T loadContactType(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
