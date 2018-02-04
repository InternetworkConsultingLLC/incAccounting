package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class PaymentTypesRow extends Row implements PaymentTypesInterface {
	public PaymentTypesRow() { 
		super(); 
		setSqlTableName("payment types");
		setSqlSecurableGuid("c32658a8c3f5921d71fb05e31d906bd5");
	}
	public static String TABLE_NAME = "payment types";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String ACCOUNTS_GUID = "Accounts GUID";
	public boolean setAccountsGuid(java.lang.String value) throws Exception { return set(ACCOUNTS_GUID, value); }
	public java.lang.String getAccountsGuid() { return (java.lang.String) get(ACCOUNTS_GUID); }
	
	public static String IS_SALES_RELATED = "Is Sales Related";
	public boolean setIsSalesRelated(java.lang.Boolean value) throws Exception { return set(IS_SALES_RELATED, value); }
	public java.lang.Boolean getIsSalesRelated() { return (java.lang.Boolean) get(IS_SALES_RELATED); }
	

	// child loaders
	
	protected Object lstPaymentTypesDocumentTypesChildren = null;
	public <T extends PaymentTypesDocumentTypesRow> List<T> loadPaymentTypesDocumentTypes(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstPaymentTypesDocumentTypesChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"payment types document types\" WHERE \"Payment Types GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstPaymentTypesDocumentTypesChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstPaymentTypesDocumentTypesChildren;
	}
	
	protected Object lstPaymentsChildren = null;
	public <T extends PaymentsRow> List<T> loadPayments(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstPaymentsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"payments\" WHERE \"Payment Types GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstPaymentsChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstPaymentsChildren;
	}
	

	// parent loaders
	
	protected Object rTransactionTypeParent = null;
	public <T extends TransactionTypesRow> T loadTransactionType(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rTransactionTypeParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"transaction types\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique transaction types row by GUID (" + Statement.convertObjectToString(this.getGuid(), null) + ")!");
			rTransactionTypeParent = lst.get(0);
		}
		return (T) rTransactionTypeParent;
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
	
	public static <T extends PaymentTypesRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"payment types\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique payment types row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
}
