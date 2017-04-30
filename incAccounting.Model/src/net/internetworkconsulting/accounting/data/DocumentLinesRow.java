/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class DocumentLinesRow extends Row implements DocumentLinesInterface {
	public DocumentLinesRow() { 
		super(); 
		setSqlTableName("document lines");
		setSqlSecurableGuid("3043f061a1f7a25decc51c7ecb71e8d2");
	}
	public static String TABLE_NAME = "document lines";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String DOCUMENTS_GUID = "Documents GUID";
	public boolean setDocumentsGuid(java.lang.String value) throws Exception { return set(DOCUMENTS_GUID, value); }
	public java.lang.String getDocumentsGuid() { return (java.lang.String) get(DOCUMENTS_GUID); }
	
	public static String SORT_ORDER = "Sort Order";
	public boolean setSortOrder(java.lang.Integer value) throws Exception { return set(SORT_ORDER, value); }
	public java.lang.Integer getSortOrder() { return (java.lang.Integer) get(SORT_ORDER); }
	
	public static String QUANTITY = "Quantity";
	public boolean setQuantity(java.math.BigDecimal value) throws Exception { return set(QUANTITY, value); }
	public java.math.BigDecimal getQuantity() { return (java.math.BigDecimal) get(QUANTITY); }
	
	public static String ITEMS_GUID = "Items GUID";
	public boolean setItemsGuid(java.lang.String value) throws Exception { return set(ITEMS_GUID, value); }
	public java.lang.String getItemsGuid() { return (java.lang.String) get(ITEMS_GUID); }
	
	public static String UNIT_MEASURES_GUID = "Unit Measures GUID";
	public boolean setUnitMeasuresGuid(java.lang.String value) throws Exception { return set(UNIT_MEASURES_GUID, value); }
	public java.lang.String getUnitMeasuresGuid() { return (java.lang.String) get(UNIT_MEASURES_GUID); }
	
	public static String DESCRIPTION = "Description";
	public boolean setDescription(java.lang.String value) throws Exception { return set(DESCRIPTION, value); }
	public java.lang.String getDescription() { return (java.lang.String) get(DESCRIPTION); }
	
	public static String UNIT_PRICE = "Unit Price";
	public boolean setUnitPrice(java.math.BigDecimal value) throws Exception { return set(UNIT_PRICE, value); }
	public java.math.BigDecimal getUnitPrice() { return (java.math.BigDecimal) get(UNIT_PRICE); }
	
	public static String EXTENSION = "Extension";
	public boolean setExtension(java.math.BigDecimal value) throws Exception { return set(EXTENSION, value); }
	public java.math.BigDecimal getExtension() { return (java.math.BigDecimal) get(EXTENSION); }
	
	public static String IS_TAXED = "Is Taxed";
	public boolean setIsTaxed(java.lang.Boolean value) throws Exception { return set(IS_TAXED, value); }
	public java.lang.Boolean getIsTaxed() { return (java.lang.Boolean) get(IS_TAXED); }
	
	public static String IS_BACKWARDS_CALCULATED = "Is Backwards Calculated";
	public boolean setIsBackwardsCalculated(java.lang.Boolean value) throws Exception { return set(IS_BACKWARDS_CALCULATED, value); }
	public java.lang.Boolean getIsBackwardsCalculated() { return (java.lang.Boolean) get(IS_BACKWARDS_CALCULATED); }
	
	public static String JOBS_GUID = "Jobs GUID";
	public boolean setJobsGuid(java.lang.String value) throws Exception { return set(JOBS_GUID, value); }
	public java.lang.String getJobsGuid() { return (java.lang.String) get(JOBS_GUID); }
	
	public static String DEPARTMENTS_GUID = "Departments GUID";
	public boolean setDepartmentsGuid(java.lang.String value) throws Exception { return set(DEPARTMENTS_GUID, value); }
	public java.lang.String getDepartmentsGuid() { return (java.lang.String) get(DEPARTMENTS_GUID); }
	
	public static String ACCOUNTS_GUID = "Accounts GUID";
	public boolean setAccountsGuid(java.lang.String value) throws Exception { return set(ACCOUNTS_GUID, value); }
	public java.lang.String getAccountsGuid() { return (java.lang.String) get(ACCOUNTS_GUID); }
	

	// child loaders
	
	protected Object lstDecreasingItemPostingsChildren = null;
	public <T extends ItemPostingsRow> List<T> loadDecreasingItemPostings(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstDecreasingItemPostingsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"item postings\" WHERE \"Decreasing Document Lines GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstDecreasingItemPostingsChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstDecreasingItemPostingsChildren;
	}
	
	protected Object lstIncreasingItemPostingsChildren = null;
	public <T extends ItemPostingsRow> List<T> loadIncreasingItemPostings(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstIncreasingItemPostingsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"item postings\" WHERE \"Increasing Document Lines GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstIncreasingItemPostingsChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstIncreasingItemPostingsChildren;
	}
	

	// parent loaders
	
	protected Object rDocumentParent = null;
	public <T extends DocumentsRow> T loadDocument(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rDocumentParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"documents\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getDocumentsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique documents row by GUID (" + Statement.convertObjectToString(this.getDocumentsGuid(), null) + ")!");
			rDocumentParent = lst.get(0);
		}
		return (T) rDocumentParent;
	}
	
	protected Object rItemParent = null;
	public <T extends ItemsRow> T loadItem(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rItemParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"items\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getItemsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique items row by GUID (" + Statement.convertObjectToString(this.getItemsGuid(), null) + ")!");
			rItemParent = lst.get(0);
		}
		return (T) rItemParent;
	}
	
	protected Object rUnitMeasureParent = null;
	public <T extends UnitMeasuresRow> T loadUnitMeasure(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rUnitMeasureParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"unit measures\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getUnitMeasuresGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique unit measures row by GUID (" + Statement.convertObjectToString(this.getUnitMeasuresGuid(), null) + ")!");
			rUnitMeasureParent = lst.get(0);
		}
		return (T) rUnitMeasureParent;
	}
	
	protected Object rJobParent = null;
	public <T extends JobsRow> T loadJob(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rJobParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"jobs\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getJobsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique jobs row by GUID (" + Statement.convertObjectToString(this.getJobsGuid(), null) + ")!");
			rJobParent = lst.get(0);
		}
		return (T) rJobParent;
	}
	
	protected Object rDepartmentParent = null;
	public <T extends DepartmentsRow> T loadDepartment(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rDepartmentParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"departments\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getDepartmentsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique departments row by GUID (" + Statement.convertObjectToString(this.getDepartmentsGuid(), null) + ")!");
			rDepartmentParent = lst.get(0);
		}
		return (T) rDepartmentParent;
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
	
	public static <T extends DocumentLinesRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"document lines\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique document lines row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
}
