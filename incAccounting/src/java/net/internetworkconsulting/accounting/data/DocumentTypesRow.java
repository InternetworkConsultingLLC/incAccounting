package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class DocumentTypesRow extends Row implements DocumentTypesInterface {
	public DocumentTypesRow() { 
		super(); 
		setSqlTableName("document types");
		setSqlSecurableGuid("964ab9ae4128f16ff6b0f9cabddeff92");
	}
	public static String TABLE_NAME = "document types";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String IS_SALES_RELATED = "Is Sales Related";
	public boolean setIsSalesRelated(java.lang.Boolean value) throws Exception { return set(IS_SALES_RELATED, value); }
	public java.lang.Boolean getIsSalesRelated() { return (java.lang.Boolean) get(IS_SALES_RELATED); }
	
	public static String IS_CREDIT_MEMO = "Is Credit Memo";
	public boolean setIsCreditMemo(java.lang.Boolean value) throws Exception { return set(IS_CREDIT_MEMO, value); }
	public java.lang.Boolean getIsCreditMemo() { return (java.lang.Boolean) get(IS_CREDIT_MEMO); }
	
	public static String IS_POSTING = "Is Posting";
	public boolean setIsPosting(java.lang.Boolean value) throws Exception { return set(IS_POSTING, value); }
	public java.lang.Boolean getIsPosting() { return (java.lang.Boolean) get(IS_POSTING); }
	
	public static String ACCOUNTS_GUID = "Accounts GUID";
	public boolean setAccountsGuid(java.lang.String value) throws Exception { return set(ACCOUNTS_GUID, value); }
	public java.lang.String getAccountsGuid() { return (java.lang.String) get(ACCOUNTS_GUID); }
	

	// child loaders
	
	protected Object lstDocumentsChildren = null;
	public <T extends DocumentsRow> List<T> loadDocuments(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstDocumentsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"documents\" WHERE \"Document Types GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstDocumentsChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstDocumentsChildren;
	}
	
	protected Object lstPaymentTypesDocumentTypesChildren = null;
	public <T extends PaymentTypesDocumentTypesRow> List<T> loadPaymentTypesDocumentTypes(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstPaymentTypesDocumentTypesChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"payment types document types\" WHERE \"Document Types GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstPaymentTypesDocumentTypesChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstPaymentTypesDocumentTypesChildren;
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
	
	public static <T extends DocumentTypesRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"document types\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique document types row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
}
