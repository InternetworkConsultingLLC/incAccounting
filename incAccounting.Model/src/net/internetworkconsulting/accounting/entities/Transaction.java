package net.internetworkconsulting.accounting.entities;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;
import java.util.List;
import net.internetworkconsulting.accounting.data.TransactionLinesRow;
import net.internetworkconsulting.accounting.data.TransactionsRow;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.mysql.Statement;

public class Transaction extends TransactionsRow {	
	private Object lstTransactionLinesChildren = null;
	public <T extends TransactionLinesRow> List<T> loadTransactionLines(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstTransactionLinesChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Transaction Lines\" WHERE \"Transactions GUID\"={PRIMARYKEY} ORDER BY \"" + TransactionLine.SORT_ORDER + "\"");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstTransactionLinesChildren = adapter.load(model, stmt, true);
		}

		if(lstTransactionLinesChildren != null) {
			List<T> lstT = (List<T>) lstTransactionLinesChildren;
			for(T obj: lstT) {
				TransactionLine line = (TransactionLine) obj;
				line.setTransaction(this);
			}
		}
		
		return (List<T>) lstTransactionLinesChildren;
	}

	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
		this.setDate(new Date(Instant.now().toEpochMilli()));
		this.setTransactionTypesGuid(TransactionType.TRANSACTION_GUID);
	}
	private String sDebitsAndCredits = "";
	public BigDecimal calculate(AdapterInterface adapter) throws Exception {
		sDebitsAndCredits = "";
		List<TransactionLine> lstLines = loadTransactionLines(adapter, TransactionLine.class, false);
		BigDecimal dBalance = new BigDecimal(0);
		for(TransactionLine tl : lstLines) {
			if(tl.getDebit() != null && tl.getDebit().compareTo(BigDecimal.ZERO) != 0) {
				dBalance = dBalance.add(tl.getDebit());
				sDebitsAndCredits = sDebitsAndCredits + tl.getDescription() + " $ " + tl.getDebit() + "\n";
			}
		}
		sDebitsAndCredits = sDebitsAndCredits + "Balance $ " + dBalance;
		return dBalance;
	}
	
	private boolean bSkipDocumentCheck = false;
	public void setSkipDocumentCheck(boolean value) { bSkipDocumentCheck = value; }
	public boolean getSkipDocumentCheck() { return bSkipDocumentCheck; }
	
	public void beforeSave(AdapterInterface adapter) throws Exception {
		if(!getSkipDocumentCheck() && !getTransactionTypesGuid().equals(TransactionType.TRANSACTION_GUID))
			throw new Exception("You cannot edit this transaction - it's a document!");
		
		List<TransactionLine> lstLines = loadTransactionLines(adapter, TransactionLine.class, false);
//		if(lstLines.size() < 1)
//			throw new Exception("Cannot save a transaction with no lines!");
		
		BigDecimal dBalance = calculate(adapter);
		if(dBalance.compareTo(BigDecimal.ZERO) != 0)
			throw new Exception("This transaction does not follow the 'debits must equal credits' rule!  Please balance before saving!\n\n" + sDebitsAndCredits);
	}
	public void afterSave(AdapterInterface adapter) throws Exception {
		String sql = "DELETE FROM \"%s\" WHERE \"%s\" = 0";
		sql = String.format(sql, TransactionLine.TABLE_NAME, TransactionLine.DEBIT);
		adapter.execute(new Statement(sql), false);
	}

	public boolean isReconciled(AdapterInterface adapter) throws Exception {
		boolean ret = false;
		
		List<TransactionLine> lstLines = loadTransactionLines(adapter, TransactionLine.class, false);
		for(TransactionLine line : lstLines)
			if(line.getReconciliationsGuid() != null)
				ret = true;
		
		return ret;
	}
	public static boolean isNumberAvailable(AdapterInterface adapter, String reference_number, String account_guid) throws Exception {
		Statement stmt = new Statement(adapter.getSession().readJar(Transaction.class, "Transaction.isAvailable.sql"));
		stmt.getParameters().put("@Account", account_guid);
		stmt.getParameters().put("@Reference", reference_number);
		
		List<Transaction> lst = adapter.load(Transaction.class, stmt, true);
		return lst.isEmpty();				
	}
}
