/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class ItemPostingsRow extends Row implements ItemPostingsInterface {
	public ItemPostingsRow() { 
		super(); 
		setSqlTableName("Item Postings");
		setSqlSecurableGuid("73959ce86f1e21271e60f96ba411b5ef");
	}
	public static String TABLE_NAME = "Item Postings";

	// columns
	
	public static String GUID = "GUID";
	public boolean setGuid(java.lang.String value) throws Exception { return set(GUID, value); }
	public java.lang.String getGuid() { return (java.lang.String) get(GUID); }
	
	public static String INCREASING_DOCUMENT_LINES_GUID = "Increasing Document Lines GUID";
	public boolean setIncreasingDocumentLinesGuid(java.lang.String value) throws Exception { return set(INCREASING_DOCUMENT_LINES_GUID, value); }
	public java.lang.String getIncreasingDocumentLinesGuid() { return (java.lang.String) get(INCREASING_DOCUMENT_LINES_GUID); }
	
	public static String DECREASING_DOCUMENT_LINES_GUID = "Decreasing Document Lines GUID";
	public boolean setDecreasingDocumentLinesGuid(java.lang.String value) throws Exception { return set(DECREASING_DOCUMENT_LINES_GUID, value); }
	public java.lang.String getDecreasingDocumentLinesGuid() { return (java.lang.String) get(DECREASING_DOCUMENT_LINES_GUID); }
	
	public static String DECREASE_QUANTITY = "Decrease Quantity";
	public boolean setDecreaseQuantity(java.math.BigDecimal value) throws Exception { return set(DECREASE_QUANTITY, value); }
	public java.math.BigDecimal getDecreaseQuantity() { return (java.math.BigDecimal) get(DECREASE_QUANTITY); }
	

	// child loaders
	

	// parent loaders
	
	protected Object rIncreasingDocumentLineParent = null;
	public <T extends DocumentLinesRow> T loadIncreasingDocumentLine(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rIncreasingDocumentLineParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Document Lines\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getIncreasingDocumentLinesGuid());
			List<T> lst = adapter.load(model, stmt);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique Document Lines row by GUID (" + Statement.convertObjectToString(this.getIncreasingDocumentLinesGuid(), null) + ")!");
			rIncreasingDocumentLineParent = lst.get(0);
		}
		return (T) rIncreasingDocumentLineParent;
	}
	
	protected Object rDecreasingDocumentLineParent = null;
	public <T extends DocumentLinesRow> T loadDecreasingDocumentLine(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rDecreasingDocumentLineParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Document Lines\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getDecreasingDocumentLinesGuid());
			List<T> lst = adapter.load(model, stmt);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique Document Lines row by GUID (" + Statement.convertObjectToString(this.getDecreasingDocumentLinesGuid(), null) + ")!");
			rDecreasingDocumentLineParent = lst.get(0);
		}
		return (T) rDecreasingDocumentLineParent;
	}
	

	// unique key loaders
	
	public static <T extends ItemPostingsRow> T loadByGuid(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
		String sql = "SELECT * FROM \"Item Postings\" WHERE \"GUID\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique Item Postings row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);		
	}
	

	// load all
	public static <T extends ItemPostingsRow> List<T> loadAll(AdapterInterface adapter, Class model) throws Exception {
		Statement stmt = new Statement("SELECT * FROM \"Item Postings\"");
		return (List<T>) adapter.load(model, stmt);
	}
}
