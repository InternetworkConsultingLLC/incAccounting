package net.internetworkconsulting.accounting.entities;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;
import java.util.List;
import net.internetworkconsulting.accounting.data.BankDepositsRow;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.mysql.Statement;

public class Deposit extends BankDepositsRow {
	public static String TRANSACTION_TYPE_GUID = "bbb9a35380834fe9976ad7184976f0d6";
	public static String SETTING_LAST_NUMBER = "Deposit - Lastes Number";

	private List<Payment> lstPayments = null;
	public List<Payment> loadPaymentSelections(AdapterInterface adapter, boolean force) throws Exception {
		if(lstPayments != null && !force)
			return lstPayments;
		
		Statement stmt = new Statement(adapter.getSession().readJar(Deposit.class, "Deposit.loadPaymentSelections.sql"));
		stmt.getParameters().put("{Deposits GUID}", getGuid());
		lstPayments = adapter.load(Payment.class, stmt, true);
		
		return lstPayments;
	}	
	public void calculate(AdapterInterface adapter) throws Exception {
		BigDecimal dTotal = BigDecimal.ZERO;
		int iItems = 0;
		
		List<Payment> lstPayments = loadPaymentSelections(adapter, false);
		for(Payment payment: lstPayments) {
			if(payment.getBankDepositsGuid() != null) {
				iItems = iItems + 1;
				dTotal = dTotal.add(payment.getTotal());
			}
		}
		
		setItems(iItems);
		setTotal(dTotal);
	}

	public void initialize() throws Exception {
		setGuid(User.newGuid());
		setDate(new Date(Instant.now().toEpochMilli()));
		setTotal(BigDecimal.ZERO);
		setItems(0);
	}

	public void post(AdapterInterface adapter) throws Exception {
		List<Payment> lstPayments = this.loadPayments(adapter, Payment.class, true);
		
		Transaction objTran = new Transaction();
		objTran.initialize();
		objTran.setDate(getDate());
		objTran.setGuid(getGuid());
		objTran.setReferenceNumber(getNumber());
		objTran.setTransactionTypesGuid(Deposit.TRANSACTION_TYPE_GUID);
		
		List<TransactionLine> lstLines = objTran.loadTransactionLines(adapter, TransactionLine.class, true);
		for(Payment payment: lstPayments) {
			TransactionLine line = new TransactionLine();
			line.initialize(objTran, adapter);
			line.setAccountsGuid(payment.getPostedAccountsGuid());
			line.setDebit(payment.getTotal().multiply(BigDecimal.valueOf(-1)));
			line.setDescription("Deposit " + getNumber() + "'s payment from " + payment.getContactsDisplayName() + " numbered " + payment.getTheirNumber());
			lstLines.add(line);
		}
		
		TransactionLine line = new TransactionLine();
		line.initialize(objTran, adapter);
		line.setGuid(getGuid());
		line.setAccountsGuid(getAccountsGuid());
		line.setDebit(getTotal());
		line.setDescription("Bank Deposit " + getNumber());
		lstLines.add(line);
		
		try {
			adapter.begin(false);

			objTran.setSkipDocumentCheck(true);		
			adapter.save(Transaction.TABLE_NAME, objTran);
			adapter.save(TransactionLine.TABLE_NAME, lstLines);

			setSkipTransactionCheck(true);
			setPostedTransactionsGuid(objTran.getGuid());
			adapter.save(Deposit.TABLE_NAME, this);

			adapter.commit(false);			
		}
		catch(Exception ex) {
			adapter.rollback(false);
			throw ex;
		}
	}
	public void unpost(AdapterInterface adapter) throws Exception {
		Transaction objTran = loadTransaction(adapter, Transaction.class, true);
		List<TransactionLine> lstLines = objTran.loadTransactionLines(adapter, TransactionLine.class, true);
		
		for(TransactionLine line: lstLines) {
			line.setIsDeleted(true);
		}
		objTran.setIsDeleted(true);
		
		try {
			adapter.begin(false);

			this.setSkipTransactionCheck(true);
			this.setPostedTransactionsGuid(null);
			adapter.save(Deposit.TABLE_NAME, this);
			
			objTran.setSkipDocumentCheck(true);
			adapter.save(TransactionLine.TABLE_NAME, lstLines);
			adapter.save(Transaction.TABLE_NAME, objTran);

			adapter.commit(false);
		}
		catch(Exception ex) {
			adapter.rollback(true);
			throw ex;
		}
	}

	private boolean bSkipTransactionCheck = false;
	private void setSkipTransactionCheck(boolean value) { bSkipTransactionCheck = true; }

	public static List<Deposit> loadByPosted(AdapterInterface adapter, boolean posted) throws Exception {
		String sql = "SELECT * FROM \"TABLE\" WHERE \"COL\" IS NOT NULL";
		if(!posted)
			sql = sql.replace(" NOT ", " ");
		sql = sql.replace("TABLE", Deposit.TABLE_NAME);
		sql = sql.replace("COL", Deposit.POSTED_TRANSACTIONS_GUID);
		
		Statement stmt = new Statement(sql);
		return adapter.load(Deposit.class, stmt, true);
	}


	public void beforeSave(AdapterInterface adapter) throws Exception {
		if(!bSkipTransactionCheck && getPostedTransactionsGuid() != null)
			throw new Exception("You may not change a deposit that has been posted!");
		
		if(getNumber() == null || getNumber().isEmpty()) {
			Setting bizSetting = Setting.loadByKey(adapter, Setting.class, Deposit.SETTING_LAST_NUMBER);		
			String sMyNumber = bizSetting.getValue();
			do {
				sMyNumber = net.internetworkconsulting.data.Helper.Increment(sMyNumber);
			} while(!Deposit.isNumberAvaiable(adapter, sMyNumber));

			this.setNumber(sMyNumber);
			bizSetting.setValue(sMyNumber);

			adapter.save(Setting.TABLE_NAME, bizSetting);
		}
	}	

	private static boolean isNumberAvaiable(AdapterInterface adapter, String number) throws Exception {
		String sql = "SELECT * FROM \"%s\" WHERE \"%s\"={Reference}";
		sql = String.format(sql, Payment.TABLE_NAME, Payment.PAYMENT_TYPES_GUID, Payment.OUR_NUMBER, Payment.POSTED_ACCOUNTS_GUID);
		
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{Reference}", number);
		
		List<Deposit> lst = adapter.load(Deposit.class, stmt, true);
		return lst.isEmpty();
	}

}
