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
import java.util.List;
import net.internetworkconsulting.accounting.data.TransactionLinesRow;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.mysql.Statement;

public class TransactionLine extends TransactionLinesRow {
	public static String SETTING_DEBIT_DECIMALS = "Transaction Line Setting Decimals";

	public void setTransaction(Transaction value) { 
		rTransactionParent = value; 
	}
	
	public void initialize() throws Exception { throw new Exception("You must use TransactionLine.initialize(parent, adapter)!"); }
	public void initialize(Transaction Model, AdapterInterface adapter) throws Exception {
		this.setGuid(User.newGuid());
		this.setTransactionsGuid(Model.getGuid());
		this.setTransaction(Model);
		
		this.setDebit(BigDecimal.ZERO);
		
		List<TransactionLine> lstLines = Model.loadTransactionLines(adapter, TransactionLine.class, false);
		int iMaxSortOrder = -1;
		for(TransactionLine tl : lstLines)
			if(tl.getSortOrder() > iMaxSortOrder)
				iMaxSortOrder = tl.getSortOrder();
		
		this.setSortOrder(iMaxSortOrder + 1);
	}

	public void beforeSave(AdapterInterface adapter) throws Exception {
		Transaction myTransaction = (Transaction) rTransactionParent;
		if(myTransaction != null && !myTransaction.getSkipDocumentCheck())
			if(!myTransaction.getTransactionTypesGuid().equals(TransactionType.TRANSACTION_GUID))
				throw new Exception("You cannot edit a transaction line - it's a document line!");
	}
	public void afterSave(AdapterInterface adapter) throws Exception {
		String sql = "DELETE FROM \"%s\" WHERE \"%s\" = 0";
		sql = String.format(sql, TransactionLine.TABLE_NAME, TransactionLine.DEBIT);
		adapter.execute(new Statement(sql), false);
	}
	
	public static <T extends TransactionLine> List<T> loadByAccountAndDates(AdapterInterface adapter, Class model, String account_guid, String starting_date, String ending_date) throws Exception {
		String sql = User.readJarFile(TransactionLine.class, "TransactionLine.loadByAccountAndDates.sql");
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{Account}", account_guid);
		stmt.getParameters().put("{Starting}", starting_date);
		stmt.getParameters().put("{Ending}", ending_date);
		return adapter.load(model, stmt);
	}

}
