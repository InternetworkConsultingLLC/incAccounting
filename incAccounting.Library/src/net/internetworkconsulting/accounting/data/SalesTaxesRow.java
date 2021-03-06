package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class SalesTaxesRow extends Row implements SalesTaxesInterface {
	public SalesTaxesRow() { 
		super(); 
		setSqlTableName("sales taxes");
		setSqlSecurableGuid("1ab6e3428ea9a6ecb869bf90b2b2e54a");
	}
	public static String TABLE_NAME = "sales taxes";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String CONTACTS_GUID = "Contacts GUID";
	public boolean setContactsGuid(java.lang.String value) throws Exception { return set(CONTACTS_GUID, value); }
	public java.lang.String getContactsGuid() { return (java.lang.String) get(CONTACTS_GUID); }
	
	public static String IS_GROUP = "Is Group";
	public boolean setIsGroup(java.lang.Boolean value) throws Exception { return set(IS_GROUP, value); }
	public java.lang.Boolean getIsGroup() { return (java.lang.Boolean) get(IS_GROUP); }
	
	public static String DISPLAY_NAME = "Display Name";
	public boolean setDisplayName(java.lang.String value) throws Exception { return set(DISPLAY_NAME, value); }
	public java.lang.String getDisplayName() { return (java.lang.String) get(DISPLAY_NAME); }
	
	public static String TAX_RATE = "Tax Rate";
	public boolean setTaxRate(java.math.BigDecimal value) throws Exception { return set(TAX_RATE, value); }
	public java.math.BigDecimal getTaxRate() { return (java.math.BigDecimal) get(TAX_RATE); }
	
	public static String ACCOUNTS_GUID = "Accounts GUID";
	public boolean setAccountsGuid(java.lang.String value) throws Exception { return set(ACCOUNTS_GUID, value); }
	public java.lang.String getAccountsGuid() { return (java.lang.String) get(ACCOUNTS_GUID); }
	

	// child loaders
	
	protected Object lstDocumentsChildren = null;
	public <T extends DocumentsRow> List<T> loadDocuments(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstDocumentsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"documents\" WHERE \"Sales Taxes GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstDocumentsChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstDocumentsChildren;
	}
	
	protected Object lstChildTaxMembershipsChildren = null;
	public <T extends SalesTaxMembershipsRow> List<T> loadChildTaxMemberships(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstChildTaxMembershipsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"sales tax memberships\" WHERE \"Child Sales Taxes GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstChildTaxMembershipsChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstChildTaxMembershipsChildren;
	}
	
	protected Object lstParentTaxMembershipsChildren = null;
	public <T extends SalesTaxMembershipsRow> List<T> loadParentTaxMemberships(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstParentTaxMembershipsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"sales tax memberships\" WHERE \"Parent Sales Taxes GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstParentTaxMembershipsChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstParentTaxMembershipsChildren;
	}
	

	// parent loaders
	
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
	
	protected Object rAccountParent = null;
	public <T extends AccountsRow> T loadAccount(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rAccountParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"accounts\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getAccountsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique accounts row by GUID (" + Statement.convertObjectToString(this.getAccountsGuid(), null) + ")!");
			rAccountParent = lst.get(0);
		}
		return (T) rAccountParent;
	}
	

	// unique key loaders
	
	public static <T extends SalesTaxesRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"sales taxes\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique sales taxes row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
	public static <T extends SalesTaxesRow> T loadByDisplayName(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"sales taxes\" WHERE \"Display Name\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique sales taxes row by 'Display Name': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
}
