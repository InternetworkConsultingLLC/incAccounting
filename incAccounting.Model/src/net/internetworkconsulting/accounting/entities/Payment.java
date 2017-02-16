/*
 * Copyright (C) 2016 Internetwork Consulting LLC
 *
 * This program is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by the Free 
 * Software Foundation, version 3 of the License.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for 
 * more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see http://www.gnu.org/licenses/.
 */
package net.internetworkconsulting.accounting.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import net.internetworkconsulting.accounting.data.PaymentsRow;
import net.internetworkconsulting.bootstrap.entities.User;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.Row;
import net.internetworkconsulting.data.mysql.Statement;

public class Payment extends PaymentsRow {
	public static List loadSearch(AdapterInterface adapter, List<String> columns, String search) throws Exception {
		return Row.loadSearch(adapter, ContactType.class, columns, search);
	}
	public static List getSearchColumns() {
		LinkedList<String> lstColumns = new LinkedList<>();
		lstColumns.add(Payment.DATE);
		lstColumns.add(TransactionType.NAME);
		lstColumns.add(Payment.OUR_NUMBER);
		lstColumns.add(Payment.CONTACTS_DISPLAY_NAME);
		lstColumns.add(Payment.THEIR_NUMBER);
		lstColumns.add(Payment.TOTAL);
		return lstColumns;
	}

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
		
		Statement stmt = new Statement(adapter.getSession().readFile("sql/Payment.loadPaymentApplicationSelection.sql"));
		stmt.getParameters().put("{Contacts GUID}", getContactsGuid());
		stmt.getParameters().put("{Payment Types GUID}", getPaymentTypesGuid());
		stmt.getParameters().put("{Payments GUID}", getGuid());
		
		lstPaymentApplicationSelection = adapter.load(PaymentApplicationSelection.class, stmt);
		return lstPaymentApplicationSelection;
	}
	
	public void handlePaymentType(AdapterInterface adapter) throws Exception {
		PaymentType pt = loadPaymentType(adapter, PaymentType.class, true);

		lstPaymentApplicationSelection = null;
		lstPaymentApplicationsChildren = null;

		this.setPostedAccountsGuid(pt.getAccountsGuid());
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
			if(sel.getRowState() == RowState.Update) {
				PaymentApplication app = null;
				try { app = PaymentApplication.loadByDocumentAndPayment(adapter, sel.getDocumentsGuid(), sel.getPaymentsGuid()); }
				catch(Exception ex) {
					if(sel.getAmount() == null || sel.getAmount().compareTo(BigDecimal.ZERO) == 0)
						continue;
					
					app = new PaymentApplication();
					app.setDocumentsGuid(sel.getDocumentsGuid());
					app.setPaymentsGuid(sel.getPaymentsGuid());
				}

				if(sel.getAmount() != null && sel.getBalance() != null && sel.getAmount().abs().compareTo(sel.getBalance().abs()) > 0)
					throw new Exception("Your payment is more than the balance!");
				
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
				lstLines.addAll(createCashLines(adapter, app, tran, doc, docType, tranType));
			else {
				// accural accouting
				if(doc.getPostedAccountsGuid() == null || doc.getPostedTransactionsGuid() == null )
					throw new Exception("The document must be posted in order to post a payment referencing that document!");

				lstLines.addAll(createAccuralLines(adapter, app, tran, doc, docType, tranType));
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
	private List<TransactionLine> createCashLines(AdapterInterface adapter, PaymentApplication app, Transaction tran, Document doc, DocumentType docType, TransactionType tranType) throws Exception {
		List<TransactionLine> lst = new LinkedList<>();
		
		BigDecimal dPercentagePaid = app.getAmount().divide(doc.getTotal(), RoundingMode.HALF_UP);
		
		List<TransactionLine> lstLines = doc.createPostLines(adapter, tran);
		BigDecimal dTotalApplied = BigDecimal.ZERO;
		for(int cnt = 0; cnt < lstLines.size() - 1; cnt++) {
			BigDecimal dValue = lstLines.get(cnt).getDebit().multiply(dPercentagePaid);
			dValue = Document.round(adapter, dValue, Document.SETTING_MONEY_DECIMALS);
			lstLines.get(cnt).setDebit(dValue);
			dTotalApplied = dTotalApplied.add(dValue);
			lst.add(lstLines.get(cnt));
		}		
		
		if(dTotalApplied.compareTo(app.getAmount()) != 0)
			throw new Exception("The applied amount does not equal to the rounded document rows amounts!");
		
		return lst;
	}
	private List<TransactionLine> createAccuralLines(AdapterInterface adapter, PaymentApplication app, Transaction tran, Document doc, DocumentType docType, TransactionType tranType) throws Exception {
		List<TransactionLine> lst = new LinkedList<>();

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
		
		return lst;
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
		
		return adapter.load(Payment.class, stmt);		
	}
	
}