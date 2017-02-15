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
import java.sql.Date;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import net.internetworkconsulting.accounting.data.TransactionLinesRow;
import net.internetworkconsulting.accounting.data.TransactionsRow;
import net.internetworkconsulting.bootstrap.entities.User;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.Row;
import net.internetworkconsulting.data.mysql.Statement;

public class Transaction extends TransactionsRow {	
	public static List loadSearch(AdapterInterface adapter, List<String> columns, String search) throws Exception { return Row.loadSearch(adapter, Transaction.class, columns, search); }
	public static List getSearchColumns() {
		LinkedList<String> lstColumns = new LinkedList<>();
		lstColumns.add(Transaction.DATE);
		lstColumns.add(Transaction.REFERENCE_NUMBER);
		lstColumns.add(TransactionType.NAME);
		return lstColumns;
	}
	private Object lstTransactionLinesChildren = null;
	public <T extends TransactionLinesRow> List<T> loadTransactionLines(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstTransactionLinesChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"transaction lines\" WHERE \"Transactions GUID\"={PRIMARYKEY} ORDER BY \"" + TransactionLine.SORT_ORDER + "\"");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstTransactionLinesChildren = adapter.load(model, stmt);
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
	public BigDecimal calculate(AdapterInterface adapter) throws Exception {
		List<TransactionLine> lstLines = loadTransactionLines(adapter, TransactionLine.class, false);
		BigDecimal dBalance = new BigDecimal(0);
		for(TransactionLine tl : lstLines) {
			if(tl.getDebit() != null)
				dBalance = dBalance.add(tl.getDebit());
		}
		return dBalance;
	}
	
	private boolean bSkipDocumentCheck = false;
	public void setSkipDocumentCheck(boolean value) { bSkipDocumentCheck = value; }
	public boolean getSkipDocumentCheck() { return bSkipDocumentCheck; }
	
	public void beforeSave(AdapterInterface adapter) throws Exception {
		if(!getSkipDocumentCheck())
			if(!getTransactionTypesGuid().equals(TransactionType.TRANSACTION_GUID))
				throw new Exception("You cannot edit this transaction - it's a document!");
		
		List<TransactionLine> lstLines = loadTransactionLines(adapter, TransactionLine.class, false);
		if(lstLines.size() < 1)
			throw new Exception("Cannot save a transaction with no lines!");
		
		BigDecimal dBalance = calculate(adapter);
		if(dBalance.compareTo(BigDecimal.ZERO) != 0)
			throw new Exception("This transaction does not follow the 'debits must equal credits' rule!  Please balance before saving!");
	}	
}
