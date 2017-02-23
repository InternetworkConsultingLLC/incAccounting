/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class DepartmentsRow extends Row implements DepartmentsInterface {
	public DepartmentsRow() { 
		super(); 
		setSqlTableName("Departments");
		setSqlSecurableGuid("c8cf2b64be19b0234578a5b582f86a87");
	}
	public static String TABLE_NAME = "Departments";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String PARENT_DEPARTMENTS_GUID = "Parent Departments GUID";
	public boolean setParentDepartmentsGuid(java.lang.String value) throws Exception { return set(PARENT_DEPARTMENTS_GUID, value); }
	public java.lang.String getParentDepartmentsGuid() { return (java.lang.String) get(PARENT_DEPARTMENTS_GUID); }
	
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
	public <T extends DepartmentsRow> List<T> loadChildren(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstChildrenChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Departments\" WHERE \"Parent Departments GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstChildrenChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstChildrenChildren;
	}
	
	protected Object lstDocumentLinesChildren = null;
	public <T extends DocumentLinesRow> List<T> loadDocumentLines(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstDocumentLinesChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Document Lines\" WHERE \"Departments GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstDocumentLinesChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstDocumentLinesChildren;
	}
	
	protected Object lstTransactionLinesChildren = null;
	public <T extends TransactionLinesRow> List<T> loadTransactionLines(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstTransactionLinesChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Transaction Lines\" WHERE \"Departments GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstTransactionLinesChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstTransactionLinesChildren;
	}
	

	// parent loaders
	
	protected Object rParentDepartmentParent = null;
	public <T extends DepartmentsRow> T loadParentDepartment(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rParentDepartmentParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Departments\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getParentDepartmentsGuid());
			List<T> lst = adapter.load(model, stmt);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique Departments row by GUID (" + Statement.convertObjectToString(this.getParentDepartmentsGuid(), null) + ")!");
			rParentDepartmentParent = lst.get(0);
		}
		return (T) rParentDepartmentParent;
	}
	

	// unique key loaders
	
	public static <T extends DepartmentsRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"Departments\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique Departments row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
	public static <T extends DepartmentsRow> T loadByNumber(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"Departments\" WHERE \"Number\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique Departments row by 'Number': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	

	// load all
	public static <T extends DepartmentsRow> List<T> loadAll(AdapterInterface adapter, Class model) throws Exception {
		Statement stmt = new Statement("SELECT * FROM \"Departments\"");
		return (List<T>) adapter.load(model, stmt);
	}
}
