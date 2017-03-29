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
import net.internetworkconsulting.accounting.data.ReconciliationsRow;
import net.internetworkconsulting.accounting.data.TransactionLinesRow;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.Row;
import net.internetworkconsulting.data.mysql.Statement;

public class Reconciliation extends ReconciliationsRow {

	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
		this.setDate(new Date(Instant.now().toEpochMilli()));
	}

	private static List<Option> lstOptions;
	public static List<Option> loadOptions(AdapterInterface adapter, boolean force) throws Exception {
		if(lstOptions != null && !force)
			return lstOptions;

		Statement stmt = new Statement(adapter.getSession().readJar(Reconciliation.class, "Reconciliation.loadOptions.sql"));		
		List<Option> lst = adapter.load(Option.class, stmt, true);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		lstOptions = lst;
		return lst;
	}

	private Object lstTransactionLinesChildren = null;
	public <T extends TransactionLinesRow> List<T> loadTransactionLines(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(getDate() == null || getAccountsGuid() == null)
			return new LinkedList<T>();
		
		if(lstTransactionLinesChildren == null || force) {
			Statement stmt = new Statement(adapter.getSession().readJar(Reconciliation.class, "Reconciliation.loadTransactionLines.sql"));
			stmt.getParameters().put("{RECONCILIATIONS GUID}", this.getGuid());
			stmt.getParameters().put("{ACCOUNTS GUID}", this.getAccountsGuid());
			stmt.getParameters().put("{DATE}", this.getDate());
			lstTransactionLinesChildren = adapter.load(model, stmt, true);
		}
		
		return (List<T>) lstTransactionLinesChildren;
	}
	
	public void beforeSave(AdapterInterface adapter) throws Exception {
		this.calculateBalanceOffBy(adapter);
	}

	public BigDecimal calculateBeginningBalance(AdapterInterface adapter) throws Exception {
		BigDecimal dBeginningBalance = new BigDecimal(0);
		if(getDate() == null || getAccountsGuid() == null)
			return new BigDecimal(0);
		
		Statement stmt = new Statement(adapter.getSession().readJar(Reconciliation.class, "Reconciliation.calculateBeginningBalance.sql"));
		stmt.getParameters().put("{Date}", getDate());
		stmt.getParameters().put("{Accounts GUID}", getAccountsGuid());
		stmt.getParameters().put("{Reconciliations GUID}", getGuid());
		List<Row> lst = adapter.load(Row.class, stmt, true);
		if(lst.size() != 1)
			return new BigDecimal(0);
		

		dBeginningBalance = (BigDecimal) lst.get(0).get("Balance");
		return dBeginningBalance;
	}
	public BigDecimal calculateCleared(AdapterInterface adapter) throws Exception {
		BigDecimal dCleared = new BigDecimal(0);
		
		List<TransactionLine> lstLines = loadTransactionLines(adapter, TransactionLine.class, false);
		for(TransactionLine tl : lstLines) {
			if(tl.getReconciliationsGuid() != null)
				dCleared = dCleared.add(tl.getDebit());
		}
		
		return dCleared;
	}
	public BigDecimal calculateUncleared(AdapterInterface adapter) throws Exception {
		BigDecimal dUncleared = new BigDecimal(0);
		
		List<TransactionLine> lstLines = loadTransactionLines(adapter, TransactionLine.class, false);
		for(TransactionLine tl : lstLines) {
			if(tl.getReconciliationsGuid() == null)
				dUncleared = dUncleared.add(tl.getDebit());
		}
		
		return dUncleared;
	}
	public BigDecimal calculateEndingBalance(AdapterInterface adapter) throws Exception {
		return calculateBeginningBalance(adapter).add(calculateCleared(adapter));
	}
	public BigDecimal calculateBalanceOffBy(AdapterInterface adapter) throws Exception {
		BigDecimal dStatementBalance = new BigDecimal(0);
		if(getStatementEndingBalance() != null)
			dStatementBalance = getStatementEndingBalance();
		
		this.setOffBy(calculateEndingBalance(adapter).subtract(dStatementBalance));
		
		return getOffBy();
	}
	public BigDecimal calculateLedgerBalance(AdapterInterface adapter) throws Exception {
		return calculateBeginningBalance(adapter).add(calculateUncleared(adapter)).add(calculateCleared(adapter));
	}
}
