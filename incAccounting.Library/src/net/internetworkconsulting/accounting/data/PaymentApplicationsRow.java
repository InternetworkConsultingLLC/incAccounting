package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.*;
import net.internetworkconsulting.data.mysql.*;


public class PaymentApplicationsRow extends Row implements PaymentApplicationsInterface {
	public PaymentApplicationsRow() { 
		super(); 
		setSqlTableName("payment applications");
		setSqlSecurableGuid("dc9053af5ada18d44d18f57eb907cdb4");
	}
	public static String TABLE_NAME = "payment applications";

	// columns
	
	public static String PAYMENTS_GUID = "Payments GUID";
	public boolean setPaymentsGuid(java.lang.String value) throws Exception { return set(PAYMENTS_GUID, value); }
	public java.lang.String getPaymentsGuid() { return (java.lang.String) get(PAYMENTS_GUID); }
	
	public static String DOCUMENTS_GUID = "Documents GUID";
	public boolean setDocumentsGuid(java.lang.String value) throws Exception { return set(DOCUMENTS_GUID, value); }
	public java.lang.String getDocumentsGuid() { return (java.lang.String) get(DOCUMENTS_GUID); }
	
	public static String AMOUNT = "Amount";
	public boolean setAmount(java.math.BigDecimal value) throws Exception { return set(AMOUNT, value); }
	public java.math.BigDecimal getAmount() { return (java.math.BigDecimal) get(AMOUNT); }
	

	// child loaders
	

	// parent loaders
	
	protected Object rPaymentParent = null;
	public <T extends PaymentsRow> T loadPayment(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(rPaymentParent == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"payments\" WHERE \"GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getPaymentsGuid());
			List<T> lst = adapter.load(model, stmt, true);
			if(lst.size() != 1)
				throw new Exception("Could not locate unique payments row by GUID (" + Statement.convertObjectToString(this.getPaymentsGuid(), null) + ")!");
			rPaymentParent = lst.get(0);
		}
		return (T) rPaymentParent;
	}
	
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
	

	// unique key loaders
	
}
