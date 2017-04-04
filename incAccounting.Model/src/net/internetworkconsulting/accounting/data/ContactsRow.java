/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class ContactsRow extends Row implements ContactsInterface {
	public ContactsRow() { 
		super(); 
		setSqlTableName("Contacts");
		setSqlSecurableGuid("9aa698f602b1e5694855cee73a683488");
	}
	public static String TABLE_NAME = "Contacts";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String PARENT_CONTACTS_GUID = "Parent Contacts GUID";
	public boolean setParentContactsGuid(java.lang.String value) throws Exception { return set(PARENT_CONTACTS_GUID, value); }
	public java.lang.String getParentContactsGuid() { return (java.lang.String) get(PARENT_CONTACTS_GUID); }
	
	public static String DISPLAY_NAME = "Display Name";
	public boolean setDisplayName(java.lang.String value) throws Exception { return set(DISPLAY_NAME, value); }
	public java.lang.String getDisplayName() { return (java.lang.String) get(DISPLAY_NAME); }
	
	public static String IS_ALLOWED = "Is Allowed";
	public boolean setIsAllowed(java.lang.Boolean value) throws Exception { return set(IS_ALLOWED, value); }
	public java.lang.Boolean getIsAllowed() { return (java.lang.Boolean) get(IS_ALLOWED); }
	
	public static String CONTACT_SINCE = "Contact Since";
	public boolean setContactSince(java.sql.Date value) throws Exception { return set(CONTACT_SINCE, value); }
	public java.sql.Date getContactSince() { return (java.sql.Date) get(CONTACT_SINCE); }
	
	public static String DEFAULT_SHIPPING_CONTACTS_GUID = "Default Shipping Contacts GUID";
	public boolean setDefaultShippingContactsGuid(java.lang.String value) throws Exception { return set(DEFAULT_SHIPPING_CONTACTS_GUID, value); }
	public java.lang.String getDefaultShippingContactsGuid() { return (java.lang.String) get(DEFAULT_SHIPPING_CONTACTS_GUID); }
	
	public static String WEBSITE = "Website";
	public boolean setWebsite(java.lang.String value) throws Exception { return set(WEBSITE, value); }
	public java.lang.String getWebsite() { return (java.lang.String) get(WEBSITE); }
	
	public static String EMAIL_ADDRESS = "Email Address";
	public boolean setEmailAddress(java.lang.String value) throws Exception { return set(EMAIL_ADDRESS, value); }
	public java.lang.String getEmailAddress() { return (java.lang.String) get(EMAIL_ADDRESS); }
	
	public static String OFFICE_PHONE = "Office Phone";
	public boolean setOfficePhone(java.lang.String value) throws Exception { return set(OFFICE_PHONE, value); }
	public java.lang.String getOfficePhone() { return (java.lang.String) get(OFFICE_PHONE); }
	
	public static String MOBILE_PHONE = "Mobile Phone";
	public boolean setMobilePhone(java.lang.String value) throws Exception { return set(MOBILE_PHONE, value); }
	public java.lang.String getMobilePhone() { return (java.lang.String) get(MOBILE_PHONE); }
	
	public static String HOME_PHONE = "Home Phone";
	public boolean setHomePhone(java.lang.String value) throws Exception { return set(HOME_PHONE, value); }
	public java.lang.String getHomePhone() { return (java.lang.String) get(HOME_PHONE); }
	
	public static String FAX_NUMBER = "Fax Number";
	public boolean setFaxNumber(java.lang.String value) throws Exception { return set(FAX_NUMBER, value); }
	public java.lang.String getFaxNumber() { return (java.lang.String) get(FAX_NUMBER); }
	
	public static String LINE_1 = "Line 1";
	public boolean setLine1(java.lang.String value) throws Exception { return set(LINE_1, value); }
	public java.lang.String getLine1() { return (java.lang.String) get(LINE_1); }
	
	public static String LINE_2 = "Line 2";
	public boolean setLine2(java.lang.String value) throws Exception { return set(LINE_2, value); }
	public java.lang.String getLine2() { return (java.lang.String) get(LINE_2); }
	
	public static String COUNTRY = "Country";
	public boolean setCountry(java.lang.String value) throws Exception { return set(COUNTRY, value); }
	public java.lang.String getCountry() { return (java.lang.String) get(COUNTRY); }
	
	public static String CITY = "City";
	public boolean setCity(java.lang.String value) throws Exception { return set(CITY, value); }
	public java.lang.String getCity() { return (java.lang.String) get(CITY); }
	
	public static String STATE = "State";
	public boolean setState(java.lang.String value) throws Exception { return set(STATE, value); }
	public java.lang.String getState() { return (java.lang.String) get(STATE); }
	
	public static String POSTAL_CODE = "Postal Code";
	public boolean setPostalCode(java.lang.String value) throws Exception { return set(POSTAL_CODE, value); }
	public java.lang.String getPostalCode() { return (java.lang.String) get(POSTAL_CODE); }
	
	public static String TAX_GROUP_GUID = "Tax Group GUID";
	public boolean setTaxGroupGuid(java.lang.String value) throws Exception { return set(TAX_GROUP_GUID, value); }
	public java.lang.String getTaxGroupGuid() { return (java.lang.String) get(TAX_GROUP_GUID); }
	
	public static String CONTACT_TYPES_GUID = "Contact Types GUID";
	public boolean setContactTypesGuid(java.lang.String value) throws Exception { return set(CONTACT_TYPES_GUID, value); }
	public java.lang.String getContactTypesGuid() { return (java.lang.String) get(CONTACT_TYPES_GUID); }
	

	// child loaders
	
	protected Object lstContactNotesChildren = null;
	public <T extends ContactNotesRow> List<T> loadContactNotes(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstContactNotesChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Contact Notes\" WHERE \"Contacts GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstContactNotesChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstContactNotesChildren;
	}
	
	protected Object lstShippingParentContactChildren = null;
	public <T extends ContactsRow> List<T> loadShippingParentContact(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstShippingParentContactChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Contacts\" WHERE \"Default Shipping Contacts GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstShippingParentContactChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstShippingParentContactChildren;
	}
	
	protected Object lstChildrenContactsChildren = null;
	public <T extends ContactsRow> List<T> loadChildrenContacts(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstChildrenContactsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Contacts\" WHERE \"Parent Contacts GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstChildrenContactsChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstChildrenContactsChildren;
	}
	
	protected Object lstBillingDocumentsChildren = null;
	public <T extends DocumentsRow> List<T> loadBillingDocuments(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstBillingDocumentsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Documents\" WHERE \"Billing Contacts GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstBillingDocumentsChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstBillingDocumentsChildren;
	}
	
	protected Object lstDocumentsChildren = null;
	public <T extends DocumentsRow> List<T> loadDocuments(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstDocumentsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Documents\" WHERE \"Contacts GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstDocumentsChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstDocumentsChildren;
	}
	
	protected Object lstShippingDocumentsChildren = null;
	public <T extends DocumentsRow> List<T> loadShippingDocuments(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstShippingDocumentsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Documents\" WHERE \"Shipping Contacts GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstShippingDocumentsChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstShippingDocumentsChildren;
	}
	
	protected Object lstEmployeeChildren = null;
	public <T extends EmployeesRow> List<T> loadEmployee(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstEmployeeChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Employees\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstEmployeeChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstEmployeeChildren;
	}
	
	protected Object lstSubordinatesChildren = null;
	public <T extends EmployeesRow> List<T> loadSubordinates(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstSubordinatesChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Employees\" WHERE \"Supervisor Contacts GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstSubordinatesChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstSubordinatesChildren;
	}
	
	protected Object lstItemsChildren = null;
	public <T extends ItemsRow> List<T> loadItems(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstItemsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Items\" WHERE \"Purchase Contacts GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstItemsChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstItemsChildren;
	}
	
	protected Object lstJobsChildren = null;
	public <T extends JobsRow> List<T> loadJobs(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstJobsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Jobs\" WHERE \"Contacts GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstJobsChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstJobsChildren;
	}
	
	protected Object lstBillingPaymentsChildren = null;
	public <T extends PaymentsRow> List<T> loadBillingPayments(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstBillingPaymentsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Payments\" WHERE \"Billing Contacts GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstBillingPaymentsChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstBillingPaymentsChildren;
	}
	
	protected Object lstContactPaymentsChildren = null;
	public <T extends PaymentsRow> List<T> loadContactPayments(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstContactPaymentsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Payments\" WHERE \"Contacts GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstContactPaymentsChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstContactPaymentsChildren;
	}
	
	protected Object lstRegisterEntriesChildren = null;
	public <T extends RegisterEntriesRow> List<T> loadRegisterEntries(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstRegisterEntriesChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Register Entries\" WHERE \"Contacts GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstRegisterEntriesChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstRegisterEntriesChildren;
	}
	
	protected Object lstSalesTaxesChildren = null;
	public <T extends SalesTaxesRow> List<T> loadSalesTaxes(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstSalesTaxesChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Sales Taxes\" WHERE \"Contacts GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstSalesTaxesChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstSalesTaxesChildren;
	}
	

	// parent loaders
	
	protected Object rParentContactParent = null;
	public <T extends ContactsRow> T loadParentContact(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rParentContactParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Contacts\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getParentContactsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique Contacts row by GUID (" + Statement.convertObjectToString(this.getParentContactsGuid(), null) + ")!");
			rParentContactParent = lst.get(0);
		}
		return (T) rParentContactParent;
	}
	
	protected Object rDefaultShippingContactParent = null;
	public <T extends ContactsRow> T loadDefaultShippingContact(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rDefaultShippingContactParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Contacts\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getDefaultShippingContactsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique Contacts row by GUID (" + Statement.convertObjectToString(this.getDefaultShippingContactsGuid(), null) + ")!");
			rDefaultShippingContactParent = lst.get(0);
		}
		return (T) rDefaultShippingContactParent;
	}
	
	protected Object rContactTypeParent = null;
	public <T extends ContactTypesRow> T loadContactType(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rContactTypeParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Contact Types\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getContactTypesGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique Contact Types row by GUID (" + Statement.convertObjectToString(this.getContactTypesGuid(), null) + ")!");
			rContactTypeParent = lst.get(0);
		}
		return (T) rContactTypeParent;
	}
	

	// unique key loaders
	
	public static <T extends ContactsRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"Contacts\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique Contacts row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	

	// load all
	public static <T extends ContactsRow> List<T> loadAll(AdapterInterface adapter, Class model) throws Exception {
		Statement stmt = new Statement("SELECT * FROM \"Contacts\"");
		return (List<T>) adapter.load(model, stmt, true);
	}
}
