/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class ConversionsRow extends Row implements ConversionsInterface {
	public ConversionsRow() { 
		super(); 
		setSqlTableName("Conversions");
		setSqlSecurableGuid("6332798b12e537b25b1c6ad254e14f54");
	}
	public static String TABLE_NAME = "Conversions";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String LEFT_UNIT_MEASURES_GUID = "Left Unit Measures GUID";
	public boolean setLeftUnitMeasuresGuid(java.lang.String value) throws Exception { return set(LEFT_UNIT_MEASURES_GUID, value); }
	public java.lang.String getLeftUnitMeasuresGuid() { return (java.lang.String) get(LEFT_UNIT_MEASURES_GUID); }
	
	public static String LEFT_QUANTITY = "Left Quantity";
	public boolean setLeftQuantity(java.math.BigDecimal value) throws Exception { return set(LEFT_QUANTITY, value); }
	public java.math.BigDecimal getLeftQuantity() { return (java.math.BigDecimal) get(LEFT_QUANTITY); }
	
	public static String RIGHT_UNIT_MEASURES_GUID = "Right Unit Measures GUID";
	public boolean setRightUnitMeasuresGuid(java.lang.String value) throws Exception { return set(RIGHT_UNIT_MEASURES_GUID, value); }
	public java.lang.String getRightUnitMeasuresGuid() { return (java.lang.String) get(RIGHT_UNIT_MEASURES_GUID); }
	
	public static String RIGHT_QUANTITY = "Right Quantity";
	public boolean setRightQuantity(java.math.BigDecimal value) throws Exception { return set(RIGHT_QUANTITY, value); }
	public java.math.BigDecimal getRightQuantity() { return (java.math.BigDecimal) get(RIGHT_QUANTITY); }
	

	// child loaders
	

	// parent loaders
	
	protected Object rLeftUnitMeasureParent = null;
	public <T extends UnitMeasuresRow> T loadLeftUnitMeasure(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rLeftUnitMeasureParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Unit Measures\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getLeftUnitMeasuresGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique Unit Measures row by GUID (" + Statement.convertObjectToString(this.getLeftUnitMeasuresGuid(), null) + ")!");
			rLeftUnitMeasureParent = lst.get(0);
		}
		return (T) rLeftUnitMeasureParent;
	}
	
	protected Object rRightUnitMeasureParent = null;
	public <T extends UnitMeasuresRow> T loadRightUnitMeasure(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rRightUnitMeasureParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Unit Measures\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getRightUnitMeasuresGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique Unit Measures row by GUID (" + Statement.convertObjectToString(this.getRightUnitMeasuresGuid(), null) + ")!");
			rRightUnitMeasureParent = lst.get(0);
		}
		return (T) rRightUnitMeasureParent;
	}
	

	// unique key loaders
	
	public static <T extends ConversionsRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"Conversions\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique Conversions row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	

	// load all
	public static <T extends ConversionsRow> List<T> loadAll(AdapterInterface adapter, Class model) throws Exception {
		Statement stmt = new Statement("SELECT * FROM \"Conversions\"");
		return (List<T>) adapter.load(model, stmt, true);
	}
}
