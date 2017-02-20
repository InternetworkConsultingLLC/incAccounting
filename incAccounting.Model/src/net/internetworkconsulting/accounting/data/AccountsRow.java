/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class AccountsRow extends Row implements AccountsInterface {
	public AccountsRow() { 
		super(); 
		setSqlTableName("Accounts");
		setSqlSecurableGuid("9b945efebb006547a94415eadaa12921");
	}
	public static String TABLE_NAME = "Accounts";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String ACCOUNT_TYPES_GUID = "Account Types GUID";
	public boolean setAccountTypesGuid(java.lang.String value) throws Exception { return set(ACCOUNT_TYPES_GUID, value); }
	public java.lang.String getAccountTypesGuid() { return (java.lang.String) get(ACCOUNT_TYPES_GUID); }
	
	public static String PARENT_ACCOUNTS_GUID = "Parent Accounts GUID";
	public boolean setParentAccountsGuid(java.lang.String value) throws Exception { return set(PARENT_ACCOUNTS_GUID, value); }
	public java.lang.String getParentAccountsGuid() { return (java.lang.String) get(PARENT_ACCOUNTS_GUID); }
	
	public static String SEGMENT = "Segment";
	public boolean setSegment(java.lang.String value) throws Exception { return set(SEGMENT, value); }
	public java.lang.String getSegment() { return (java.lang.String) get(SEGMENT); }
	
	public static String NUMBER = "Number";
	public boolean setNumber(java.lang.String value) throws Exception { return set(NUMBER, value); }
	public java.lang.String getNumber() { return (java.lang.String) get(NUMBER); }
	
	public static String NAME = "Name";
	public boolean setName(java.lang.String value) throws Exception { return set(NAME, value); }
	public java.lang.String getName() { return (java.lang.String) get(NAME); }
	
	public static String NESTED_NAME = "Nested Name";
	public boolean setNestedName(java.lang.String value) throws Exception { return set(NESTED_NAME, value); }
	public java.lang.String getNestedName() { return (java.lang.String) get(NESTED_NAME); }
	
	public static String IS_ALLOWED = "Is Allowed";
	public boolean setIsAllowed(java.lang.Boolean value) throws Exception { return set(IS_ALLOWED, value); }
	public java.lang.Boolean getIsAllowed() { return (java.lang.Boolean) get(IS_ALLOWED); }
	

	// child loaders
	
	protected Object lstChildrenChildren = null;
	public <T extends AccountsRow> List<T> loadChildren(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstChildrenChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Accounts\" WHERE \"Parent Accounts GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstChildrenChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstChildrenChildren;
	}
	
	protected Object lstDocumentLinesChildren = null;
	public <T extends DocumentLinesRow> List<T> loadDocumentLines(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstDocumentLinesChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Document Lines\" WHERE \"Accounts GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstDocumentLinesChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstDocumentLinesChildren;
	}
	
	protected Object lstDocumentTypesChildren = null;
	public <T extends DocumentTypesRow> List<T> loadDocumentTypes(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstDocumentTypesChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Document Types\" WHERE \"Accounts GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstDocumentTypesChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstDocumentTypesChildren;
	}
	
	protected Object lstDocumentsChildren = null;
	public <T extends DocumentsRow> List<T> loadDocuments(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstDocumentsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Documents\" WHERE \"Posted Accounts GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstDocumentsChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstDocumentsChildren;
	}
	
	protected Object lstInventoryItemsChildren = null;
	public <T extends ItemsRow> List<T> loadInventoryItems(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstInventoryItemsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Items\" WHERE \"Inventory Accounts GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstInventoryItemsChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstInventoryItemsChildren;
	}
	
	protected Object lstPurchaseItemsChildren = null;
	public <T extends ItemsRow> List<T> loadPurchaseItems(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstPurchaseItemsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Items\" WHERE \"Purchase Accounts GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstPurchaseItemsChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstPurchaseItemsChildren;
	}
	
	protected Object lstSalesItemsChildren = null;
	public <T extends ItemsRow> List<T> loadSalesItems(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstSalesItemsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Items\" WHERE \"Sales Accounts GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstSalesItemsChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstSalesItemsChildren;
	}
	
	protected Object lstPaymentTypesChildren = null;
	public <T extends PaymentTypesRow> List<T> loadPaymentTypes(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstPaymentTypesChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Payment Types\" WHERE \"Accounts GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstPaymentTypesChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstPaymentTypesChildren;
	}
	
	protected Object lstPaymentsChildren = null;
	public <T extends PaymentsRow> List<T> loadPayments(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstPaymentsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Payments\" WHERE \"Posted Accounts GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstPaymentsChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstPaymentsChildren;
	}
	
	protected Object lstPayrollChecksChildren = null;
	public <T extends PayrollChecksRow> List<T> loadPayrollChecks(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstPayrollChecksChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Payroll Checks\" WHERE \"Accounts GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstPayrollChecksChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstPayrollChecksChildren;
	}
	
	protected Object lstCreditPayrollFieldsChildren = null;
	public <T extends PayrollFieldsRow> List<T> loadCreditPayrollFields(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstCreditPayrollFieldsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Payroll Fields\" WHERE \"Credit Accounts GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstCreditPayrollFieldsChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstCreditPayrollFieldsChildren;
	}
	
	protected Object lstDebitPayrollFieldsChildren = null;
	public <T extends PayrollFieldsRow> List<T> loadDebitPayrollFields(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstDebitPayrollFieldsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Payroll Fields\" WHERE \"Debit Accounts GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstDebitPayrollFieldsChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstDebitPayrollFieldsChildren;
	}
	
	protected Object lstReconciliationsChildren = null;
	public <T extends ReconciliationsRow> List<T> loadReconciliations(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstReconciliationsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Reconciliations\" WHERE \"Accounts GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstReconciliationsChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstReconciliationsChildren;
	}
	
	protected Object lstSalesTaxesChildren = null;
	public <T extends SalesTaxesRow> List<T> loadSalesTaxes(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstSalesTaxesChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Sales Taxes\" WHERE \"Accounts GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstSalesTaxesChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstSalesTaxesChildren;
	}
	
	protected Object lstTransactionLinesChildren = null;
	public <T extends TransactionLinesRow> List<T> loadTransactionLines(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstTransactionLinesChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Transaction Lines\" WHERE \"Accounts GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstTransactionLinesChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstTransactionLinesChildren;
	}
	

	// parent loaders
	
	protected Object rAccountTypeParent = null;
	public <T extends AccountTypesRow> T loadAccountType(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rAccountTypeParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Account Types\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getAccountTypesGuid());
			List<T> lst = adapter.load(model, stmt);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique Account Types row by GUID (" + Statement.convertObjectToString(this.getAccountTypesGuid(), null) + ")!");
			rAccountTypeParent = lst.get(0);
		}
		return (T) rAccountTypeParent;
	}
	
	protected Object rParentAccountParent = null;
	public <T extends AccountsRow> T loadParentAccount(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rParentAccountParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Accounts\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getParentAccountsGuid());
			List<T> lst = adapter.load(model, stmt);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique Accounts row by GUID (" + Statement.convertObjectToString(this.getParentAccountsGuid(), null) + ")!");
			rParentAccountParent = lst.get(0);
		}
		return (T) rParentAccountParent;
	}
	

	// unique key loaders
	
	public static <T extends AccountsRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"Accounts\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique Accounts row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	

	// load all
	public static <T extends AccountsRow> List<T> loadAll(AdapterInterface adapter, Class model) throws Exception {
		Statement stmt = new Statement("SELECT * FROM \"Accounts\"");
		return (List<T>) adapter.load(model, stmt);
	}
}
