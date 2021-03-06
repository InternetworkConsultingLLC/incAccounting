package net.internetworkconsulting.accounting.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import net.internetworkconsulting.accounting.data.DocumentLinesRow;
import net.internetworkconsulting.accounting.data.DocumentsRow;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.mysql.Statement;

public class Document extends DocumentsRow {
	public static String SETTING_TERMS = "Document - Terms";
	public static String SETTING_DUE_DAYS = "Document - Due Days";
	public static String SETTING_MONEY_DECIMALS = "Document - Money Decimals";
	public static String SETTING_RATE_DECIMALS = "Document - Rate Decimals";
	public static String SETTING_QUANITY_DECIMALS = "Document - Quantity Decimals";
	public static String SETTING_POST_ON_SAVE = "Document - Post on Save";
	public static String SETTING_LAST_PURCHASE_QUOTE_NUMBER = "Document - Last Purchase Quote Number";
	public static String SETTING_LAST_PURCHASE_ORDER_NUMBER = "Document - Last Purchase Order Number";
	public static String SETTING_LAST_PURCHASE_INVOICE_NUMBER = "Document - Last Purchase Invoice Number";
	public static String SETTING_LAST_PURCHASE_CREDIT_NUMBER = "Document - Last Purchase Credit Number";
	public static String SETTING_LAST_SALES_QUOTE_NUMBER = "Document - Last Sales Quote Number";
	public static String SETTING_LAST_SALES_ORDER_NUMBER = "Document - Last Sales Order Number";
	public static String SETTING_LAST_SALES_INVOICE_NUMBER = "Document - Last Sales Invoice Number";
	public static String SETTING_LAST_SALES_CREDIT_NUMBER = "Document - Last Sales Credit Number";
	public static BigDecimal round(AdapterInterface adapter, BigDecimal original, String setting) {
		if(original == null)
			return null;
		
		int iDecimals = Integer.parseInt(adapter.getSession().getSetting(setting));
		return original.setScale(iDecimals, RoundingMode.HALF_UP);
	}
	
	public boolean getSkipPostedCheck() { return bSkipPostedCheck; }
	private boolean bSkipPostedCheck = false;
		            
	public void initialize() throws Exception { throw new Exception("Please provide an adapter and call initialize(adapter)!"); }
	public void initialize(AdapterInterface adapter) throws Exception {
		setGuid(User.newGuid());
		setDate(new Date(Instant.now().toEpochMilli()));
		setSubtotal(BigDecimal.ZERO);
		setTaxes(BigDecimal.ZERO);
		setTotal(BigDecimal.ZERO);
		setNontaxableSubtotal(BigDecimal.ZERO);
		setTaxableSubtotal(BigDecimal.ZERO);
		setTaxRate(BigDecimal.ZERO);
		
		setTerms(adapter.getSession().getSetting(SETTING_TERMS));

		int iDueDays = Integer.parseInt(adapter.getSession().getSetting(SETTING_DUE_DAYS));
		setDueDate(new Date(Instant.now().plus(iDueDays, ChronoUnit.DAYS).toEpochMilli()));
	}
	
	public static List<Document> loadByTypeAndPosted(AdapterInterface adapter, String type_guid, boolean is_posted) throws Exception {
		Statement stmt = new Statement(" SELECT * FROM \"" + Document.TABLE_NAME + "\" ");
		
		String where = " WHERE ";
		where += "\"" + Document.GUID + "\" NOT IN ( SELECT \"" + Payment.PREPAYMENT_DOCUMENTS_GUID + "\" FROM \"" + Payment.TABLE_NAME + "\" ) AND ";
		if(type_guid != null && type_guid.length() == 32) {
			where += " \"" + Document.DOCUMENT_TYPES_GUID + "\"={Type GUID} AND";
			stmt.getParameters().put("{Type GUID}", type_guid);
		} else {
			where += " \"Document Types GUID\" IN ( SELECT \"GUID\" FROM \"Document Types\" WHERE \"Accounts GUID\" IS NOT NULL) AND ";
		}
		if(is_posted) {
			where += " \"" + Document.POSTED_TRANSACTIONS_GUID + "\" IS NOT NULL AND ";
		} else {
			where += " \"" + Document.POSTED_TRANSACTIONS_GUID + "\" IS NULL AND ";
		}
		where = where.substring(0, where.length() - 4);
		if(!where.trim().equals("WHERE"))
			stmt.setCommand(stmt.getCommand() + where);
		
		return adapter.load(Document.class, stmt, true);		
	}
	
	public static List<Option> loadOptions(AdapterInterface adapter) throws Exception {
		Statement stmt = new Statement(adapter.getSession().readJar(Document.class, "Document.loadOptions.sql"));		
		List<Option> lst = adapter.load(Option.class, stmt, true);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		return lst;
	}

	public void handleDocumentTypesGuid(AdapterInterface adapter) throws Exception {
		DocumentType dt = loadDocumentType(adapter, DocumentType.class, true);
		setPostedAccountsGuid(dt.getAccountsGuid());
	}
	public void handleContactsGuid(AdapterInterface adapter) throws Exception {
		if(getContactsGuid() == null)
			return;
		
		Contact cntct = loadContact(adapter, Contact.class, true);
		if(cntct == null) {
			super.setContactsGuid(null);
			return;
		}
		
		setContactsDisplayName(cntct.getDisplayName());

		if(cntct.getTaxGroupGuid() != null)
			setSalesTaxesGuid(cntct.getTaxGroupGuid());
		
		setBillingContactsGuid(cntct.getGuid());
		handleBillingContactsGuid(adapter);
		
		setShippingContactsGuid(cntct.getGuid());
		handleShippingContactsGuid(adapter);
	}
	public void handleDate(AdapterInterface adapter) throws Exception {
		if(getDate() == null)
			return;
		
		int iDueDays = Integer.parseInt(adapter.getSession().getSetting(Document.SETTING_DUE_DAYS));
		setDueDate(new java.util.Date(Instant.now().plus(iDueDays, ChronoUnit.DAYS).toEpochMilli()));	
	}
	public void handleBillingContactsGuid(AdapterInterface adapter) throws Exception {
		if(getBillingContactsGuid() == null)
			return;
		
		Contact cntct = null;
		if(getBillingContactsGuid() != null)
			cntct = loadBillingContact(adapter, Contact.class, true);
		else
			cntct = loadContact(adapter, Contact.class, true);

		setBillingAddressDisplayName(cntct.getDisplayName());
		setBillingAddressLine1(cntct.getLine1());
		setBillingAddressLine2(cntct.getLine2());
		setBillingAddressCity(cntct.getCity());
		setBillingAddressState(cntct.getState());
		setBillingAddressPostalCode(cntct.getPostalCode());
		setBillingAddressCountry(cntct.getCountry());
		
		if(getShippingContactsGuid() == null && cntct.getTaxGroupGuid() != null)
			handleSalesTaxesGuid(adapter);
	}
	public void handleShippingContactsGuid(AdapterInterface adapter) throws Exception {
		if(getShippingContactsGuid() == null)
			return;
		
		Contact cntct = null;
		if(getShippingContactsGuid() != null)
			cntct = loadShippingContact(adapter, Contact.class, true);
		else
			cntct = loadContact(adapter, Contact.class, true);

		setShippingAddressDisplayName(cntct.getDisplayName());
		setShippingAddressLine1(cntct.getLine1());
		setShippingAddressLine2(cntct.getLine2());
		setShippingAddressCity(cntct.getCity());
		setShippingAddressState(cntct.getState());
		setShippingAddressPostalCode(cntct.getPostalCode());
		setShippingAddressCountry(cntct.getCountry());
		
		handleSalesTaxesGuid(adapter);
	}
	public void handleSalesTaxesGuid(AdapterInterface adapter) throws Exception {
		if(getSalesTaxesGuid() == null)
			return;
		
		SalesTax st = loadSalesTax(adapter, SalesTax.class, true);
		if(st == null)
			super.setSalesTaxesGuid(null);
		
		handleTaxRate(adapter);
	}
	public void handleTaxRate(AdapterInterface adapter) throws Exception {
		calculate(adapter);
	}
	public void handleAutoNumber(AdapterInterface adapter) throws Exception {
		String sKey = "";
		if(this.getDocumentTypesGuid().equals(DocumentType.PURCHASE_CREDIT_GUID))
			sKey = Document.SETTING_LAST_PURCHASE_CREDIT_NUMBER;
		else if(this.getDocumentTypesGuid().equals(DocumentType.PURCHASE_INVOICE_GUID))
			sKey = Document.SETTING_LAST_PURCHASE_INVOICE_NUMBER;
		else if(this.getDocumentTypesGuid().equals(DocumentType.PURCHASE_ORDER_GUID))
			sKey = Document.SETTING_LAST_PURCHASE_ORDER_NUMBER;
		else if(this.getDocumentTypesGuid().equals(DocumentType.PURCHASE_QUOTE_GUID))
			sKey = Document.SETTING_LAST_PURCHASE_QUOTE_NUMBER;
		else if(this.getDocumentTypesGuid().equals(DocumentType.SALES_CREDIT_GUID))
			sKey = Document.SETTING_LAST_SALES_CREDIT_NUMBER;
		else if(this.getDocumentTypesGuid().equals(DocumentType.SALES_INVOICE_GUID))
			sKey = Document.SETTING_LAST_SALES_INVOICE_NUMBER;
		else if(this.getDocumentTypesGuid().equals(DocumentType.SALES_ORDER_GUID))
			sKey = Document.SETTING_LAST_SALES_ORDER_NUMBER;
		else if(this.getDocumentTypesGuid().equals(DocumentType.SALES_QUOTE_GUID))
			sKey = Document.SETTING_LAST_SALES_QUOTE_NUMBER;
		else
			throw new Exception("Document Type " + this.getDocumentTypesGuid() + " is invalid!");
			
		adapter.begin(false);
		try {
			Setting bizSetting = Setting.loadByKey(adapter, Setting.class, sKey);		
			String sMyNumber = bizSetting.getValue();
			do {
				sMyNumber = net.internetworkconsulting.data.Helper.Increment(sMyNumber);
			} while(!Document.isNumberAvailable(adapter, this.getDocumentTypesGuid(), sMyNumber));

			this.setReferenceNumber(sMyNumber);
			bizSetting.setValue(sMyNumber);

			adapter.save(Setting.TABLE_NAME, bizSetting);
			adapter.save(Document.TABLE_NAME, this);

			adapter.commit(false);
		} catch(Exception ex) {
			adapter.rollback(false);
			throw ex;
		}
	}
	public Document handleCopy(AdapterInterface adapter, String target_type_guid) throws Exception {
		Document target = new Document();
		target.initialize(adapter);
		for(String key : this.getOriginals().keySet())
			target.getChanges().put(key, this.getOriginals().get(key));

		target.setReferenceNumber(null);
		target.setParentDocumentsGuid(this.getGuid());
		target.setGuid(User.newGuid());

		target.setDate(new Date(Instant.now().toEpochMilli()));
		target.handleDate(adapter);
		
		target.setDocumentTypesGuid(target_type_guid);
		target.handleDocumentTypesGuid(adapter);
		
		List<DocumentLine> lstSourceLines = this.loadDocumentLines(adapter, DocumentLine.class, false);
		List<DocumentLine> lstTargetLines = target.loadDocumentLines(adapter, DocumentLine.class, false);
		
		for(DocumentLine rSourceLine : lstSourceLines) {
			DocumentLine rTargetLine = new DocumentLine();
			rTargetLine.initialize(adapter, target);
			rTargetLine.setAccountsGuid(rSourceLine.getAccountsGuid());
			rTargetLine.setDepartmentsGuid(rSourceLine.getDepartmentsGuid());
			rTargetLine.setItemsGuid(rSourceLine.getItemsGuid());
			rTargetLine.setJobsGuid(rSourceLine.getJobsGuid());
			rTargetLine.setUnitMeasuresGuid(rSourceLine.getUnitMeasuresGuid());
			rTargetLine.setIsBackwardsCalculated(rSourceLine.getIsBackwardsCalculated());
			rTargetLine.setIsTaxed(rSourceLine.getIsTaxed());
			rTargetLine.setQuantity(rSourceLine.getQuantity());
			rTargetLine.setSortOrder(rSourceLine.getSortOrder());
			rTargetLine.setUnitPrice(rSourceLine.getUnitPrice());
			rTargetLine.setExtension(rSourceLine.getExtension());
			rTargetLine.setDescription(rSourceLine.getDescription());
			lstTargetLines.add(rTargetLine);
		}

		target.calculate(adapter);
		
		adapter.begin(false);
		adapter.save(Document.TABLE_NAME, target);
		adapter.save(DocumentLine.TABLE_NAME, lstTargetLines);
		adapter.commit(false);
		
		return Document.loadByGuid(adapter, Document.class, target.getGuid());
	}

	public void beforeSave(AdapterInterface adapter) throws Exception {
		Transaction tran = null;
		try { tran = loadTransaction(adapter, Transaction.class, false); }
		catch(Exception ex) {}
		if(tran != null)
			throw new Exception("You cannot change a document that has been posted!");
	}

	public BigDecimal calculate(AdapterInterface adapter) throws Exception {
		BigDecimal dOriginalTotal = getTotal();
		
		List<DocumentLine> lstLines = loadDocumentLines(adapter, DocumentLine.class, false);

		BigDecimal dTaxable = BigDecimal.ZERO;
		BigDecimal dNontaxable = BigDecimal.ZERO;				
		for(int cnt = 0; cnt < lstLines.size(); cnt++) {
			DocumentLine line = lstLines.get(cnt);
			if(line.getIsTaxed())
				dTaxable = dTaxable.add(line.getExtension());
			else
				dNontaxable = dNontaxable.add(line.getExtension());
			
			line.setExtension(Document.round(adapter, line.getExtension(), Document.SETTING_MONEY_DECIMALS));
			line.setQuantity(Document.round(adapter, line.getQuantity(), Document.SETTING_QUANITY_DECIMALS));
			line.setUnitPrice(Document.round(adapter, line.getUnitPrice(), Document.SETTING_MONEY_DECIMALS));					
		}
		
		setTaxableSubtotal(Document.round(adapter, dTaxable, Document.SETTING_MONEY_DECIMALS));
		setNontaxableSubtotal(Document.round(adapter, dNontaxable, Document.SETTING_MONEY_DECIMALS));
		
		BigDecimal dSubtotal = dTaxable.add(dNontaxable);
		setSubtotal(Document.round(adapter, dSubtotal, Document.SETTING_MONEY_DECIMALS));
		
		if(getTaxRate() == null)
			setTaxRate(BigDecimal.ZERO);

		BigDecimal dTaxes = BigDecimal.ZERO;
		if(getSalesTaxesGuid() != null) {
			SalesTax st = loadSalesTax(adapter, SalesTax.class, true);
			setTaxRate(Document.round(adapter, st.getTaxRate(), Document.SETTING_RATE_DECIMALS));
			dTaxes =  Document.round(adapter, dTaxable.multiply(st.getTaxRate()), Document.SETTING_MONEY_DECIMALS);
		}
		
		setTaxes(Document.round(adapter, dTaxes, Document.SETTING_MONEY_DECIMALS));		
		setTotal(Document.round(adapter, dSubtotal.add(dTaxes), Document.SETTING_MONEY_DECIMALS));
		
		if(dOriginalTotal == null)
			return getTotal();
		else
			return getTotal().subtract(dOriginalTotal);
	}
	public int getNextSortOrder(AdapterInterface adapter) throws Exception {
		List<DocumentLine> lstLines = loadDocumentLines(adapter, DocumentLine.class, false);
		int iMax = 0;
		for(DocumentLine dl : lstLines)
			if(dl.getSortOrder() >= iMax)
				iMax = dl.getSortOrder() + 10;
		
		return iMax;
	}
	
	public void post(AdapterInterface adapter) throws Exception {
		// Document Guid => Transaction Guid
		// Document Line Guid => Transaction Line Guid

		DocumentType docType = this.loadDocumentType(adapter, DocumentType.class, false);
		if(docType.getAccountsGuid() == null)
			throw new Exception("The document type does not have an account defined.  The document type cannot be posted!");
		
		if(getPostedAccountsGuid() == null)
			throw new Exception("The document does not have an account to post to!");
		
		Transaction objTran = new Transaction();
		objTran.initialize();
		objTran.setDate(getDate());
		objTran.setTransactionTypesGuid(getDocumentTypesGuid());
		objTran.setGuid(getGuid());
		objTran.setReferenceNumber(getReferenceNumber());
		
		List<TransactionLine> lstTranLines = createPostLines(adapter, objTran);		
		List<DocumentLine> lstDocLines = this.loadDocumentLines(adapter, DocumentLine.class, false);

		// update document to reflect posting
		this.setPostedTransactionsGuid(this.getGuid());
		try {
			adapter.begin(false);

			objTran.setSkipDocumentCheck(true);
			adapter.save(Transaction.TABLE_NAME, objTran);
			adapter.save(TransactionLine.TABLE_NAME, lstTranLines);
			objTran.setSkipDocumentCheck(false);

			this.bSkipPostedCheck = true;
			adapter.save(Document.TABLE_NAME, this, false);
			adapter.save(DocumentLine.TABLE_NAME, lstDocLines, false);
			this.bSkipPostedCheck = false;

			adapter.commit(false);
		}
		catch(Exception ex) {
			adapter.rollback(false);
			throw ex;
		}
	}
	public void unpost(AdapterInterface adapter) throws Exception {
		Transaction objTran = loadTransaction(adapter, Transaction.class, false);
		List<TransactionLine> objLines = objTran.loadTransactionLines(adapter, TransactionLine.class, false);
		List<DocumentLine> lstDocLines = this.loadDocumentLines(adapter, DocumentLine.class, false);
		
		objTran.setIsDeleted(true);
		for(TransactionLine line: objLines)
			line.setIsDeleted(true);

		List<ItemPosting> lstDeletePostings = new LinkedList<>();
		for(DocumentLine dl : lstDocLines) {
			List<ItemPosting> lstPostings = dl.loadDecreasingItemPostings(adapter, ItemPosting.class, false);
			for(ItemPosting ip : lstPostings) {
				ip.setIsDeleted(true);
				lstDeletePostings.add(ip);
			}
		}
		
		//this.setPostedAccountsGuid(null);
		this.setPostedTransactionsGuid(null);

		try {
			adapter.begin(false);

			this.bSkipPostedCheck = true;
			adapter.save(Document.TABLE_NAME, this, false);
			adapter.save(DocumentLine.TABLE_NAME, lstDocLines, false);
			this.bSkipPostedCheck = false;

			adapter.save(ItemPosting.TABLE_NAME, lstDeletePostings);
			
			objTran.setSkipDocumentCheck(true);
			adapter.save(TransactionLine.TABLE_NAME, objLines);
			adapter.save(Transaction.TABLE_NAME, objTran);
			objTran.setSkipDocumentCheck(false);

			adapter.commit(false);
		}
		catch(Exception ex) {
			adapter.rollback(false);
			throw ex;
		}
	}		
	public List<TransactionLine> createPostLines(AdapterInterface adapter, Transaction objTran) throws Exception {
		List<TransactionLine> lstTranLines =  objTran.loadTransactionLines(adapter, TransactionLine.class, false);
		//List<TransactionLine> lstTranLines = new LinkedList<TransactionLine>();
		TransactionLine objTranLine = null;
		List<DocumentLine> lstDocLines = this.loadDocumentLines(adapter, DocumentLine.class, false);
		DocumentType docType = this.loadDocumentType(adapter, DocumentType.class, true);

		// Sales	CM		Lines	Hdr		Type 
		//	T		T		1		-1		Sales Credit
		//	T		F		-1		1		Sales Invoice
		//	F		T		-1		1		Purch Credit
		//	F		F		1		-1		Purch Invoice
		
		// setup for lines
		BigDecimal dMultiplier = BigDecimal.ONE;
		if(docType.getIsCreditMemo() != docType.getIsSalesRelated())
			dMultiplier = BigDecimal.valueOf(-1);

		for(DocumentLine docLine: lstDocLines) {
			if(docLine.getAccountsGuid() == null)
				throw new Exception("One of the document lines is not associated with an account!");
			
			if(docLine.getItemsGuid() != null && docLine.loadItem(adapter, Item.class, false).getInventoryAccountsGuid() != null) {
				// inventory item
				if(docLine.getQuantity() == null)
					throw new Exception("An inventory item was encountered with no quantity!");
				
				// For inventory items...  We need to compute the quantity effect:
				// Sales	CM		Mult
				//	T		T		1
				//	T		F		-1
				//	F		T		-1
				//	F		F		1				
				int iQtyMultiple = 0;
				if(docType.getIsSalesRelated())
					iQtyMultiple = -1;
				else
					iQtyMultiple = 1;
				if(docType.getIsCreditMemo())
					iQtyMultiple = iQtyMultiple * -1;
				
				if(docLine.getQuantity().multiply(BigDecimal.valueOf(iQtyMultiple)).compareTo(BigDecimal.ZERO) > 0) {
					// increase inventory
					createPostStandard(dMultiplier, docLine, objTran, adapter);
				} else if(docLine.getQuantity().multiply(BigDecimal.valueOf(iQtyMultiple)).compareTo(BigDecimal.ZERO) < 0) {
					// decrease inventory
					createPostCostAndRevenue(dMultiplier, docLine, objTran, adapter);
				} else
					throw new Exception("An inventory item was encountered with a quantity of 0!");
			} else {
				// non-inventory item
				createPostStandard(dMultiplier, docLine, objTran, adapter);
			}
		}
		
		// add sales tax line
		SalesTax tax = this.loadSalesTax(adapter, SalesTax.class, true);
		if(tax.getAccountsGuid() == null)
			throw new Exception("The sales tax group does not have a liability account!");
				
		objTranLine = new TransactionLine();
		objTranLine.initialize(objTran, adapter);
		objTranLine.setGuid(User.newGuid());
		objTranLine.setSortOrder(lstTranLines.size());
		objTranLine.setAccountsGuid(tax.getAccountsGuid());
		objTranLine.setDebit(this.getTaxes().multiply(dMultiplier));
		objTranLine.setDepartmentsGuid(null);
		objTranLine.setDescription("Sales Tax " + tax.getDisplayName());
		objTranLine.setJobsGuid(null);
		lstTranLines.add(objTranLine);
		
		// add header line
		dMultiplier = dMultiplier.multiply(BigDecimal.valueOf(-1)); // flip the sign
		objTranLine = new TransactionLine();
		objTranLine.initialize(objTran, adapter);
		objTranLine.setGuid(User.newGuid());
		objTranLine.setSortOrder(lstTranLines.size());
		objTranLine.setAccountsGuid(getPostedAccountsGuid());
		objTranLine.setDebit(this.getTotal().multiply(dMultiplier));
		objTranLine.setDepartmentsGuid(null);
		objTranLine.setDescription("Invoice " + this.getReferenceNumber() + " Total");
		objTranLine.setJobsGuid(null);
		lstTranLines.add(objTranLine);
		
		return lstTranLines;		
	}
	private void createPostStandard(BigDecimal dMultiplier, DocumentLine docLine, Transaction objTran, AdapterInterface adapter) throws Exception {
		if(docLine.getAccountsGuid() == null)
			throw new Exception("Document line does not have an account!");
		
		TransactionLine objTranLine = new TransactionLine();
		objTranLine.initialize(objTran, adapter);
		//objTranLine.setGuid(docLine.getGuid());
		objTranLine.setSortOrder(objTran.loadTransactionLines(adapter, TransactionLine.class, false).size());
		objTranLine.setGuid(User.newGuid());
		objTranLine.setAccountsGuid(docLine.getAccountsGuid());
		objTranLine.setDebit(docLine.getExtension().multiply(dMultiplier));
		objTranLine.setDepartmentsGuid(docLine.getDepartmentsGuid());
		objTranLine.setDescription(docLine.getDescription());
		objTranLine.setJobsGuid(docLine.getJobsGuid());
		objTran.loadTransactionLines(adapter, TransactionLine.class, false).add(objTranLine);
	}
	private void createPostCostAndRevenue(BigDecimal dMultiplier, DocumentLine docLine, Transaction objTran, AdapterInterface adapter) throws Exception {
		int iQtyDecimals = Integer.parseInt(adapter.getSession().getSetting(Document.SETTING_QUANITY_DECIMALS));
		int iMoneyDecimals = Integer.parseInt(adapter.getSession().getSetting(Document.SETTING_MONEY_DECIMALS));
		
		Item item = docLine.loadItem(adapter, Item.class, false);
		BigDecimal dCosts = item.calculateCost(adapter, docLine, true);
		dCosts = new BigDecimal(dCosts.toString());
		BigDecimal dRevenue = docLine.getExtension().subtract(dCosts).setScale(iMoneyDecimals, RoundingMode.HALF_UP);
				
		TransactionLine objTranLine;

		// reduce inventory (credit $ of costs)
		objTranLine= new TransactionLine();
		objTranLine.initialize(objTran, adapter);
		//objTranLine.setGuid(docLine.getGuid());
		objTranLine.setSortOrder(objTran.loadTransactionLines(adapter, TransactionLine.class, false).size());
		objTranLine.setGuid(User.newGuid());
		objTranLine.setAccountsGuid(item.getInventoryAccountsGuid());
		objTranLine.setDebit(dCosts.multiply(new BigDecimal(-1)));
		objTranLine.setDepartmentsGuid(docLine.getDepartmentsGuid());
		objTranLine.setDescription(docLine.getDescription());
		objTranLine.setJobsGuid(docLine.getJobsGuid());
		objTran.loadTransactionLines(adapter, TransactionLine.class, false).add(objTranLine);
		

		// revenue line (credit $ of sales - costs)
		objTranLine = new TransactionLine();
		objTranLine.initialize(objTran, adapter);
		//objTranLine.setGuid(docLine.getGuid());
		objTranLine.setSortOrder(objTran.loadTransactionLines(adapter, TransactionLine.class, false).size());
		objTranLine.setGuid(User.newGuid());
		objTranLine.setAccountsGuid(docLine.getAccountsGuid());
		objTranLine.setDebit(dRevenue.multiply(new BigDecimal(-1)));
		objTranLine.setDepartmentsGuid(docLine.getDepartmentsGuid());
		objTranLine.setDescription(docLine.getDescription());
		objTranLine.setJobsGuid(docLine.getJobsGuid());
		objTran.loadTransactionLines(adapter, TransactionLine.class, false).add(objTranLine);
	}
	
	private static boolean isNumberAvailable(AdapterInterface adapter, String type_guid, String number) throws Exception {
		String sql = "SELECT * FROM \"%s\" WHERE \"%s\"={Type} AND \"%s\"={Reference}";
		sql = String.format(sql, Document.TABLE_NAME, Document.DOCUMENT_TYPES_GUID, Document.REFERENCE_NUMBER);
		
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{Type}", type_guid);
		stmt.getParameters().put("{Reference}", number);
		
		List<Document> lst = adapter.load(Document.class, stmt, true);
		return lst.isEmpty();
	}
	public boolean getIsPrepayment(AdapterInterface adapter) throws Exception {
		String sql = "SELECT * FROM \"%s\" WHERE \"%s\"={GUID}";
		Statement stmt = new Statement(String.format(sql, Payment.TABLE_NAME, Payment.PREPAYMENT_DOCUMENTS_GUID));
		stmt.getParameters().put("{GUID}", getGuid());
		List<Document> lstDocs = adapter.load(Document.class, stmt, true);
		return lstDocs.size() > 0;
	}
	
	public <T extends DocumentLinesRow> List<T> loadDocumentLines(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstDocumentLinesChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"document lines\" WHERE \"Documents GUID\"={PRIMARYKEY} ORDER BY \"Sort Order\"");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstDocumentLinesChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstDocumentLinesChildren;
	}
 }
