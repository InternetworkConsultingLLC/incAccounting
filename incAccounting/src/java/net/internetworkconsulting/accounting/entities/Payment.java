package net.internetworkconsulting.accounting.entities;

import com.ibm.icu.text.RuleBasedNumberFormat;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import net.internetworkconsulting.accounting.data.PaymentsRow;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.mysql.Statement;

public class Payment extends PaymentsRow {
	public static String SETTING_LAST_SALES_RECEIPT_NUMBER = "Payment - Last Sales Receipt Number";

	private transient AdapterInterface myAdapter = null;
 	public AdapterInterface getAdapter() { return myAdapter; }
	public void setAdapter(AdapterInterface value) { myAdapter = value; }

	public void initialize() throws Exception { throw new Exception("Please provide an adapter and call initialize(adapter)!"); }
	public void initialize(AdapterInterface adapter) throws Exception {
		myAdapter = adapter;
		
		setGuid(User.newGuid());
		setDate(new Date(Instant.now().toEpochMilli()));
		setTotal(BigDecimal.ZERO);
	}

	List<PaymentApplicationSelection> lstPaymentApplicationSelection = null;
	public List<PaymentApplicationSelection> loadPaymentApplicationSelection(AdapterInterface adapter, boolean force) throws Exception {
		if(getPaymentTypesGuid() == null)
			lstPaymentApplicationSelection = new LinkedList<PaymentApplicationSelection>();
		if(getContactsGuid() == null)
			lstPaymentApplicationSelection = new LinkedList<PaymentApplicationSelection>();
		
		if(lstPaymentApplicationSelection != null && !force)
			return lstPaymentApplicationSelection;
		
		Statement stmt = new Statement(adapter.getSession().readJar(Payment.class, "Payment.loadPaymentApplicationSelection.sql"));
		stmt.getParameters().put("@ContactsGUID", getContactsGuid());
		if(this.loadPaymentType(adapter, PaymentType.class, false).getIsSalesRelated())
			stmt.getParameters().put("@IsSalesRelated", 1);
		else
			stmt.getParameters().put("@IsSalesRelated", 0);
		stmt.getParameters().put("@PaymentsGUID", getGuid());
		
		lstPaymentApplicationSelection = adapter.load(PaymentApplicationSelection.class, stmt, true);
		
		boolean bPrepayInvoice = false;
		for(PaymentApplicationSelection pas : lstPaymentApplicationSelection)
			if(pas.getDocumentsGuid().equals(this.getPrepaymentDocumentsGuid())) {
				bPrepayInvoice = true;
				pas.setName("Prepayment");
			}
		if(!bPrepayInvoice) {
			PaymentApplicationSelection pas = new PaymentApplicationSelection();
			pas.initialize();
			pas.setAmount(BigDecimal.ZERO);
			pas.setBalance(BigDecimal.ZERO);
			pas.setDate(getDate());
			pas.setIsCreditMemo(false);
			pas.setDocumentsGuid(loadPrepaymentDocument(adapter, false).getGuid());
			pas.setName("Prepayment");
			pas.setPaymentsGuid(getGuid());
			lstPaymentApplicationSelection.add(0, pas);
		}
		
		return lstPaymentApplicationSelection;
	}
	
	public void handlePaymentType(AdapterInterface adapter) throws Exception {
		PaymentType pt = loadPaymentType(adapter, PaymentType.class, true);

		lstPaymentApplicationSelection = null;
		lstPaymentApplicationsChildren = null;

		this.setPostedAccountsGuid(pt.getAccountsGuid());
		
		if(PaymentType.SALES_PAYMENT_GUID.equals(getPaymentTypesGuid()))
			loadPrepaymentDocument(adapter, false).setDocumentTypesGuid(DocumentType.SALES_INVOICE_GUID);
		else
			loadPrepaymentDocument(adapter, false).setDocumentTypesGuid(DocumentType.PURCHASE_INVOICE_GUID);
		loadPrepaymentDocument(adapter, false).handleDocumentTypesGuid(adapter);
	}
	public void handleContact(AdapterInterface adapter) throws Exception {
		Contact contact = loadContact(adapter, Contact.class, true);
		
		lstPaymentApplicationSelection = null;
		lstPaymentApplicationsChildren = null;
		
		setBillingContactsGuid(null);
		setBillingAddressCity(null);
		setBillingAddressCountry(null);
		setBillingAddressDisplayName(null);
		setBillingAddressLine1(null);
		setBillingAddressLine2(null);
		setBillingAddressPostalCode(null);
		setBillingAddressState(null);
		
		this.setContactsDisplayName(contact.getDisplayName());
		
		loadPrepaymentDocument(adapter, false).setContactsGuid(getContactsGuid());
		loadPrepaymentDocument(adapter, false).handleContactsGuid(adapter);

	}
	public void handleBillingContact(AdapterInterface adapter) throws Exception {
		Contact contact = loadBillingContact(adapter, Contact.class, true);
		
		this.setBillingAddressDisplayName(contact.getDisplayName());
		this.setBillingAddressLine1(contact.getLine1());
		this.setBillingAddressLine2(contact.getLine2());
		this.setBillingAddressCity(contact.getCity());
		this.setBillingAddressState(contact.getState());
		this.setBillingAddressPostalCode(contact.getPostalCode());
		this.setBillingAddressCountry(contact.getCountry());
	}

	public String getTotalAsText() {
		return net.internetworkconsulting.data.Helper.numberToText("EN", "US", "dollars", getTotal());
	}	
	
	public void calculate(AdapterInterface adapter) throws Exception {
		BigDecimal bTotal = BigDecimal.ZERO;
		
		List<PaymentApplicationSelection> lstApplications = loadPaymentApplicationSelection(adapter, false);
		for(PaymentApplicationSelection pas: lstApplications)
			if(pas.getAmount() != null)
				bTotal = bTotal.add(pas.getAmount());
		
		setTotal(bTotal);
	}
	
	public void savePaymentApplicationSelections(AdapterInterface adapter) throws Exception {
		List<PaymentApplicationSelection> lstSelections = loadPaymentApplicationSelection(adapter, false);
		for(PaymentApplicationSelection sel: lstSelections) {
			if(sel.getRowState() == RowState.Update || sel.getRowState() == RowState.Insert) {
				PaymentApplication app = null;
				try { app = PaymentApplication.loadByDocumentAndPayment(adapter, sel.getDocumentsGuid(), sel.getPaymentsGuid()); }
				catch(Exception ex) {
					if(sel.getAmount() == null || sel.getAmount().compareTo(BigDecimal.ZERO) == 0)
						continue;
					
					app = new PaymentApplication();
					app.setDocumentsGuid(sel.getDocumentsGuid());
					app.setPaymentsGuid(sel.getPaymentsGuid());
				}

				if(
					sel.getAmount() != null && sel.getBalance() != null && 
					sel.getAmount().abs().compareTo(sel.getBalance().abs()) > 0 &&
					!getPrepaymentDocumentsGuid().equals(sel.getDocumentsGuid())
				)
					throw new Exception("Your payment on " + sel.getName() + " " + sel.getReferenceNumber() + " is more than it's balance!");
				
				if(sel.getAmount() == null || sel.getAmount().compareTo(BigDecimal.ZERO) == 0)
					app.setIsDeleted(true);
				else if(sel.getIsCreditMemo())
					app.setAmount(sel.getAmount().multiply(BigDecimal.valueOf(-1)));
				else
					app.setAmount(sel.getAmount());
				
				adapter.save(PaymentApplication.TABLE_NAME, app);
			}		
		}
	}
	
	public void post(AdapterInterface adapter) throws Exception {
		if(this.getPostedAccountsGuid() == null)
			throw new Exception("The payment is not associated with a cash account!");
		
		Transaction tran = new Transaction();
		tran.initialize();
		tran.setGuid(getGuid());
		tran.setReferenceNumber(getOurNumber());
		tran.setTransactionTypesGuid(getPaymentTypesGuid());
		tran.setDate(getDate());
		
		List<TransactionLine> lstLines = tran.loadTransactionLines(adapter, TransactionLine.class, true);
		List<PaymentApplication> lstApps = loadPaymentApplications(adapter, PaymentApplication.class, true);
		for(PaymentApplication app: lstApps) {
			Document doc = app.loadDocument(adapter, Document.class, true);
			DocumentType docType = doc.loadDocumentType(adapter, DocumentType.class, true);
			TransactionType tranType = docType.loadTransactionType(adapter, TransactionType.class, true);
						
			if(docType.getAccountsGuid() == null)
				// cash accounting
				createCashLines(adapter, app, tran, doc, docType, tranType);
			else {
				// accural accouting
				if(
					(doc.getPostedAccountsGuid() == null || doc.getPostedTransactionsGuid() == null ) &&
					!doc.getIsPrepayment(adapter)
				)
					throw new Exception("The document must be posted in order to post a payment referencing that document!");

				createAccuralLines(adapter, app, tran, doc, docType, tranType);
			}
		}
		
		if(getTotal().compareTo(BigDecimal.ZERO) != 0) {
			// Customer Payment
			//Ref			Debit	Credit
			//Invoice				AR		(decrease asset)
			//Check		Cash				(increase asset)

			// Vendor Paynent
			// Invoice		AP				(decrease liability)
			// Check				Cash		(decrease cash)
			PaymentType payType = loadPaymentType(adapter, PaymentType.class, true);		

			TransactionLine headerLine = new TransactionLine();
			headerLine.initialize(tran, adapter);
			headerLine.setAccountsGuid(this.getPostedAccountsGuid());

			TransactionType myTranType = payType.loadTransactionType(adapter, TransactionType.class, true);
			headerLine.setDescription(myTranType.getName() + " " + getOurNumber() + "; Their Number " + getTheirNumber());

			BigDecimal dAmount = BigDecimal.ZERO;
			if(payType.getIsSalesRelated())
				dAmount = BigDecimal.valueOf(1).multiply(getTotal());
			else
				dAmount = BigDecimal.valueOf(-1).multiply(getTotal());
			
			headerLine.setDebit(Document.round(adapter, dAmount, Document.SETTING_MONEY_DECIMALS));
			lstLines.add(headerLine);
		}
		
		try {
			adapter.begin(false);

			tran.setSkipDocumentCheck(true);
			adapter.save(Transaction.TABLE_NAME, tran);
			adapter.save(TransactionLine.TABLE_NAME, lstLines);

			this.setPostedTransactionsGuid(tran.getGuid());
			this.setSkipTransactionCheck(true);
			adapter.save(TABLE_NAME, this);

			adapter.commit(false);
		}
		catch(Exception ex) {
			adapter.rollback(false);
			throw ex;
		}
	}
	public void unpost(AdapterInterface adapter) throws Exception {
		Transaction tran = Transaction.loadByGuid(adapter, Transaction.class, getGuid());
		tran.setIsDeleted(true);

		List<TransactionLine> lines = tran.loadTransactionLines(adapter, TransactionLine.class, true);
		for(TransactionLine line: lines)
			line.setIsDeleted(true);
		
		this.setPostedTransactionsGuid(null);
		
		try {
			adapter.begin(false);

			this.setSkipTransactionCheck(true);
			adapter.save(TABLE_NAME, this);

			tran.setSkipDocumentCheck(true);

			adapter.save(TransactionLine.TABLE_NAME, lines);
			adapter.save(Transaction.TABLE_NAME, tran);


			adapter.commit(false);
		}
		catch(Exception ex) {
			adapter.rollback(false);
			throw ex;
		}
	}
	private void createCashLines(AdapterInterface adapter, PaymentApplication app, Transaction tran, Document doc, DocumentType docType, TransactionType tranType) throws Exception {
		BigDecimal dPercentagePaid = app.getAmount().divide(doc.getTotal(), RoundingMode.HALF_UP);		

		BigDecimal dTotalApplied = BigDecimal.ZERO;
		List<TransactionLine> lstLines = doc.createPostLines(adapter, tran);
		for(int cnt = 0; cnt < lstLines.size() - 1; cnt++) {			
			TransactionLine line = lstLines.get(cnt);
			
			BigDecimal dValue = line.getDebit().multiply(dPercentagePaid);
			dValue = Document.round(adapter, dValue, Document.SETTING_MONEY_DECIMALS);
			line.setDebit(dValue);
			
			dTotalApplied = dTotalApplied.add(dValue);
		}
		
		lstLines.remove(lstLines.size() - 1);
				
		if(dTotalApplied.abs().compareTo(app.getAmount().abs()) != 0)
			throw new Exception("The applied amount does not equal to the rounded document rows amounts!");
	}
	private void createAccuralLines(AdapterInterface adapter, PaymentApplication app, Transaction tran, Document doc, DocumentType docType, TransactionType tranType) throws Exception {
		List<TransactionLine> lst = tran.loadTransactionLines(adapter, TransactionLine.class, false);

		// Customer Payment
		//Ref			Debit	Credit
		//Invoice				AR		(decrease asset)
		//Check		Cash				(increase asset)
		
		// Vendor Paynent
		// Invoice		AP				(decrease liability)
		// Check				Cash		(decrease cash)
		
		// setup for lines
		BigDecimal dMultiplier;
		if(this.getPaymentTypesGuid().equals(PaymentType.PURCHASE_PAYMENT_GUID))
			dMultiplier = BigDecimal.valueOf(1);
		else if(this.getPaymentTypesGuid().equals(PaymentType.SALES_PAYMENT_GUID))
			dMultiplier = BigDecimal.valueOf(-1);
		else
			throw new Exception("Invalid payment type GUID!");
		
		if(docType.getIsCreditMemo())
			dMultiplier = dMultiplier.multiply(BigDecimal.valueOf(-1));

		TransactionLine line = new TransactionLine();
		line.initialize(tran, adapter);
		line.setAccountsGuid(doc.getPostedAccountsGuid());
		line.setDescription(tranType.getName() + " " + doc.getReferenceNumber() + "; Their Number " + doc.getCustomerReference());				
		BigDecimal dAmount = Document.round(adapter, dMultiplier.multiply(app.getAmount()), Document.SETTING_MONEY_DECIMALS);
		line.setDebit(dAmount);
		
		lst.add(line);
	}
	
	private boolean bSkipTransactionCheck = false;
	public void setSkipTransactionCheck(boolean value) { bSkipTransactionCheck = value; }

	public void beforeSave(AdapterInterface adapter) throws Exception {
		if(getPostedTransactionsGuid() != null && !bSkipTransactionCheck)
			throw new Exception("You cannot modify a posted payment!");
	}
	
	public static List<Payment> loadByTypeAndPosted(AdapterInterface adapter, String type_guid, boolean is_posted) throws Exception {
		Statement stmt = new Statement(" SELECT * FROM \"" + Payment.TABLE_NAME + "\" ");
		
		String where = " WHERE ";
		if(type_guid != null && type_guid.length() == 32) {
			where += " \"" + Payment.PAYMENT_TYPES_GUID+ "\"={Type GUID} AND";
			stmt.getParameters().put("{Type GUID}", type_guid);
		} else {
			where += " \"Payment Types GUID\" IN ( SELECT \"GUID\" FROM \"Payment Types\" ) AND ";
		}
		if(is_posted) {
			where += " \"" + Payment.POSTED_TRANSACTIONS_GUID + "\" IS NOT NULL AND ";
		} else {
			where += " \"" + Payment.POSTED_TRANSACTIONS_GUID + "\" IS NULL AND ";
		}
		where = where.substring(0, where.length() - 4);
		if(!where.trim().equals("WHERE"))
			stmt.setCommand(stmt.getCommand() + where);
		
		return adapter.load(Payment.class, stmt, true);		
	}
	
	private Document docPrepayment = null;
	public Document loadPrepaymentDocument(AdapterInterface adapter, boolean forced) throws Exception {
		if(docPrepayment == null || forced) {
			String sql = "SELECT * FROM \"%s\" WHERE \"%s\"={GUID}";
			Statement stmt = new Statement(String.format(sql, Document.TABLE_NAME, Document.GUID));
			stmt.getParameters().put("{GUID}", getPrepaymentDocumentsGuid());
			List<Document> lst = adapter.load(Document.class, stmt, true);
			if(lst.size() != 1) {
				docPrepayment = new Document();
				docPrepayment.initialize(adapter);
				docPrepayment.setReferenceNumber("Prepayment " + this.getGuid());

				this.setPrepaymentDocumentsGuid(docPrepayment.getGuid());
			} else
				docPrepayment = lst.get(0);
		}
		
		docPrepayment.setDate(this.getDate());
		
		return docPrepayment;
	}

	public void handleAutoNumber(AdapterInterface adapter) throws Exception {
		adapter.begin(false);
		try {
			if(this.getPaymentTypesGuid().equals(PaymentType.SALES_PAYMENT_GUID))
				handleSalesAutoNumber(adapter);
			else
				handlePurchaseAutoNumber(adapter);
			adapter.commit(false);
		} catch(Exception ex) {
			adapter.rollback(false);
			throw ex;
		}
	}
	private void handleSalesAutoNumber(AdapterInterface adapter) throws Exception {
		Setting bizSetting = Setting.loadByKey(adapter, Setting.class, Payment.SETTING_LAST_SALES_RECEIPT_NUMBER);		
		String sMyNumber = bizSetting.getValue();
		do {
			sMyNumber = net.internetworkconsulting.data.Helper.Increment(sMyNumber);
		} while(!Payment.isNumberAvaiable(adapter, getPaymentTypesGuid(), sMyNumber, getPostedAccountsGuid()));

		this.setOurNumber(sMyNumber);
		bizSetting.setValue(sMyNumber);

		adapter.save(Setting.TABLE_NAME, bizSetting);
		adapter.save(Payment.TABLE_NAME, this);
	}
	private void handlePurchaseAutoNumber(AdapterInterface adapter) throws Exception {
		Account bizAccount = loadAccount(adapter, Account.class, false);
		String sMyNumber = bizAccount.getLastNumber();
		boolean isAvailable = false;
		do {
			sMyNumber = net.internetworkconsulting.data.Helper.Increment(sMyNumber);
			isAvailable = Payment.isNumberAvaiable(adapter, getPaymentTypesGuid(), sMyNumber, getPostedAccountsGuid());
			/// isAvailable = isAvailable && payrollCheck.isAvailable
			isAvailable = isAvailable && PayrollCheck.isNumberAvailable(adapter, sMyNumber, getPostedAccountsGuid());
			// isAvailable= isAvailable && transaction.isNumberAvailable
			isAvailable = isAvailable && Transaction.isNumberAvailable(adapter, sMyNumber, getPostedAccountsGuid());
		} while(!isAvailable);

		this.setOurNumber(sMyNumber);
		bizAccount.setLastNumber(sMyNumber);

		adapter.save(Account.TABLE_NAME, bizAccount);
		adapter.save(Payment.TABLE_NAME, this);
	}
	public static boolean isNumberAvaiable(AdapterInterface adapter, String type_guid, String number, String account_guid) throws Exception {
		String sql = "SELECT * FROM \"%s\" WHERE \"%s\"={Type} AND \"%s\"={Reference} AND \"%s\"={Account}";
		sql = String.format(sql, Payment.TABLE_NAME, Payment.PAYMENT_TYPES_GUID, Payment.OUR_NUMBER, Payment.POSTED_ACCOUNTS_GUID);
		
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{Account}", account_guid);
		stmt.getParameters().put("{Type}", type_guid);
		stmt.getParameters().put("{Reference}", number);
		
		List<Payment> lst = adapter.load(Payment.class, stmt, true);
		return lst.isEmpty();
	}	
}
