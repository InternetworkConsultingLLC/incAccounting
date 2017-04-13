package net.internetworkconsulting.accounting.entities;

import net.internetworkconsulting.accounting.data.RegisterEntriesRow;
import net.internetworkconsulting.data.AdapterInterface;

public class RegisterEntry extends RegisterEntriesRow {
	public static final String TRANSACTION_TYPE_GUID = "38ec8da627f345eb9f0639c0931d5db8";

	public static TransactionLine create(AdapterInterface adapter, String asset_account_guid) throws Exception {
		Transaction tran = new Transaction();
		tran.initialize();
		tran.setTransactionTypesGuid(RegisterEntry.TRANSACTION_TYPE_GUID);

		TransactionLine asset_line = new TransactionLine();
		asset_line.initialize(tran, adapter);
		asset_line.setAccountsGuid(asset_account_guid);
		tran.loadTransactionLines(adapter, TransactionLine.class, false).add(asset_line);

		TransactionLine expense_line = new TransactionLine();
		expense_line.initialize(tran, adapter);
		tran.loadTransactionLines(adapter, TransactionLine.class, false).add(expense_line);
		
		RegisterEntry entry = new RegisterEntry();
		entry.initialize(asset_line);
		asset_line.loadRegisterEntries(adapter, RegisterEntry.class, false).add(entry);
		
		return asset_line;
	}
	
	public void initialize() throws Exception {
		throw new UnsupportedOperationException("Not supported!  Please use 'public void initialize(TransactionLine line)'.");
	}
	public void initialize(TransactionLine line) throws Exception {
		setGuid(line.getGuid());
	}
}
