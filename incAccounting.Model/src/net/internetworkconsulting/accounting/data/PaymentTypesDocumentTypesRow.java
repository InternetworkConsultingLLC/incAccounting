/*
 * incEntity - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import javax.xml.bind.annotation.*;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class PaymentTypesDocumentTypesRow extends Row implements PaymentTypesDocumentTypesInterface {
	public PaymentTypesDocumentTypesRow() { 
		super(); 
		setSqlTableName("Payment Types Document Types");
		setSqlSecurableGuid("7dabc46ab8f75f3d1915616830db350f");
	}
	public static String TABLE_NAME = "Payment Types Document Types";

	// columns
	
	public static String DOCUMENT_TYPES_GUID = "Document Types GUID";
	public boolean setDocumentTypesGuid(java.lang.String value) throws Exception { return set(DOCUMENT_TYPES_GUID, value); }
	public java.lang.String getDocumentTypesGuid() { return (java.lang.String) get(DOCUMENT_TYPES_GUID); }
	
	public static String PAYMENT_TYPES_GUID = "Payment Types GUID";
	public boolean setPaymentTypesGuid(java.lang.String value) throws Exception { return set(PAYMENT_TYPES_GUID, value); }
	public java.lang.String getPaymentTypesGuid() { return (java.lang.String) get(PAYMENT_TYPES_GUID); }
	

	// child loaders
	

	// parent loaders
	
	protected Object rDocumentTypesParent = null;
	public <T extends DocumentTypesRow> T loadDocumentTypes(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rDocumentTypesParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Document Types\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getDocumentTypesGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique Document Types row by GUID (" + Statement.convertObjectToString(this.getDocumentTypesGuid(), null) + ")!");
			rDocumentTypesParent = lst.get(0);
		}
		return (T) rDocumentTypesParent;
	}
	
	protected Object rPaymentTypeParent = null;
	public <T extends PaymentTypesRow> T loadPaymentType(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rPaymentTypeParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Payment Types\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getPaymentTypesGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique Payment Types row by GUID (" + Statement.convertObjectToString(this.getPaymentTypesGuid(), null) + ")!");
			rPaymentTypeParent = lst.get(0);
		}
		return (T) rPaymentTypeParent;
	}
	

	// unique key loaders
	

	// load all
	public static <T extends PaymentTypesDocumentTypesRow> List<T> loadAll(AdapterInterface adapter, Class model) throws Exception {
		Statement stmt = new Statement("SELECT * FROM \"Payment Types Document Types\"");
		return (List<T>) adapter.load(model, stmt, true);
	}
}
