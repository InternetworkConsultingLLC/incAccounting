/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class UnitMeasuresRow extends Row implements UnitMeasuresInterface {
	public UnitMeasuresRow() { 
		super(); 
		setSqlTableName("Unit Measures");
		setSqlSecurableGuid("7b75551eba1477306b17861d66595527");
	}
	public static String TABLE_NAME = "Unit Measures";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String DISPLAY_NAME = "Display Name";
	public boolean setDisplayName(java.lang.String value) throws Exception { return set(DISPLAY_NAME, value); }
	public java.lang.String getDisplayName() { return (java.lang.String) get(DISPLAY_NAME); }
	
	public static String ABBREVIATION = "Abbreviation";
	public boolean setAbbreviation(java.lang.String value) throws Exception { return set(ABBREVIATION, value); }
	public java.lang.String getAbbreviation() { return (java.lang.String) get(ABBREVIATION); }
	
	public static String IS_ALLOWED = "Is Allowed";
	public boolean setIsAllowed(java.lang.Boolean value) throws Exception { return set(IS_ALLOWED, value); }
	public java.lang.Boolean getIsAllowed() { return (java.lang.Boolean) get(IS_ALLOWED); }
	

	// child loaders
	
	protected Object lstLeftConversionsChildren = null;
	public <T extends ConversionsRow> List<T> loadLeftConversions(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstLeftConversionsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Conversions\" WHERE \"Left Unit Measures GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstLeftConversionsChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstLeftConversionsChildren;
	}
	
	protected Object lstRightConversionsChildren = null;
	public <T extends ConversionsRow> List<T> loadRightConversions(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstRightConversionsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Conversions\" WHERE \"Right Unit Measures GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstRightConversionsChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstRightConversionsChildren;
	}
	
	protected Object lstDocumentLinesChildren = null;
	public <T extends DocumentLinesRow> List<T> loadDocumentLines(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstDocumentLinesChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Document Lines\" WHERE \"Unit Measures GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstDocumentLinesChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstDocumentLinesChildren;
	}
	
	protected Object lstItemsChildren = null;
	public <T extends ItemsRow> List<T> loadItems(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstItemsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Items\" WHERE \"Inventory Unit Measures GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstItemsChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstItemsChildren;
	}
	

	// parent loaders
	

	// unique key loaders
	
	public static <T extends UnitMeasuresRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"Unit Measures\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique Unit Measures row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
	public static <T extends UnitMeasuresRow> T loadByDisplayName(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"Unit Measures\" WHERE \"Display Name\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique Unit Measures row by 'Display Name': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	
	public static <T extends UnitMeasuresRow> T loadByAbbreviation(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"Unit Measures\" WHERE \"Abbreviation\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique Unit Measures row by 'Abbreviation': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	

	// load all
	public static <T extends UnitMeasuresRow> List<T> loadAll(AdapterInterface adapter, Class model) throws Exception {
		Statement stmt = new Statement("SELECT * FROM \"Items\"");
		return (List<T>) adapter.load(model, stmt, true);
	}
}
