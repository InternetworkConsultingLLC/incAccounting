/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class PaymentTypesRow extends Row implements PaymentTypesInterface {
	public PaymentTypesRow() { 
		super(); 
		setSqlTableName("Payment Types");
		setSqlSecurableGuid("7e18f4bc2b57c8fac2939460b6740554");
	}
	public static String TABLE_NAME = "Payment Types";

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
			Statement stmt = new Statement("SELECT * FROM \"Payment Types Document Types\" WHERE \"Payment Types GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstPaymentTypesDocumentTypesChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstPaymentTypesDocumentTypesChildren;
	}
	
	protected Object lstPaymentsChildren = null;
	public <T extends PaymentsRow> List<T> loadPayments(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstPaymentsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Payments\" WHERE \"Payment Types GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstPaymentsChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstPaymentsChildren;
	}
	

	// parent loaders
	
	protected Object rTransactionTypeParent = null;
	public <T extends TransactionTypesRow> T loadTransactionType(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rTransactionTypeParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Transaction Types\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			List<T> lst = adapter.load(model, stmt);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique Transaction Types row by GUID (" + Statement.convertObjectToString(this.getGuid(), null) + ")!");
			rTransactionTypeParent = lst.get(0);
		}
		return (T) rTransactionTypeParent;
	}
	
	protected Object rAccountParent = null;
	public <T extends AccountsRow> T loadAccount(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rAccountParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Accounts\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getAccountsGuid());
			List<T> lst = adapter.load(model, stmt);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique Accounts row by GUID (" + Statement.convertObjectToString(this.getAccountsGuid(), null) + ")!");
			rAccountParent = lst.get(0);
		}
		return (T) rAccountParent;
	}
	

	// unique key loaders
	
	public static <T extends PaymentTypesRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"Payment Types\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique Payment Types row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	

	// load all
	public static <T extends PaymentTypesRow> List<T> loadAll(AdapterInterface adapter, Class model) throws Exception {
		Statement stmt = new Statement("SELECT * FROM \"Payment Types\"");
		return (List<T>) adapter.load(model, stmt);
	}
}
