package incAccounting.importSage50;

import java.util.LinkedList;
import java.util.List;
import net.internetworkconsulting.accounting.entities.Account;
import net.internetworkconsulting.accounting.entities.AccountType;
import net.internetworkconsulting.bootstrap.entities.User;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.Row;
import net.internetworkconsulting.data.StatementInterface;

public class main {
	private AdapterInterface ptAdapter;
	private AdapterInterface myAdapter;
	private User myUser;

	public static void main(String[] args) throws Exception {
		main app = new main();
		app.execute();
	}
	
	public void execute() throws Exception {		
		ptAdapter = new net.internetworkconsulting.data.pervasive.Adapter("Peachtree", "Password", "Server", "Database");
		
		myUser = new User();
		myUser.setDatabase("incllc");
		myUser.setPassword("Happy123");
		myUser.setSqlUser("administrator");
		myUser.setSqlServer("localhost");
		myAdapter = myUser.login(true);

		importAccounts();
		importGetAP();
		importGetAR();
		importCustomers();
	}
	
	private void importAccounts() throws Exception {
		String sql = "SELECT * FROM Chart";
		StatementInterface stmt = new net.internetworkconsulting.data.pervasive.Statement(sql);
		List<Row> lstPtAcounts = ptAdapter.load(Row.class, stmt);

		LinkedList<net.internetworkconsulting.accounting.entities.Account> lstMyAccounts = new LinkedList<>();
		for(Row ptAccount : lstPtAcounts) {
			net.internetworkconsulting.accounting.entities.Account myAccount = new Account();
			myAccount.initialize();

			myAccount.setName((String) ptAccount.get("AccountDescription"));
			myAccount.setSegment((String) ptAccount.get("AccountID"));
			
			int iAccountType = (int) ptAccount.get("AccountType");
			switch(iAccountType) {					
				//	0 => Cash
				//	1 => Receivable (Accounts Receivable)
				//	2 => Inventory
				//	3 => Receivables Retainage (Sage 50 Quantum Accounting only)
				//	4 => OthCurAsset (Other Current Assets)
				//	5 => FixedAsset (Fixed Assets)
				//	6 => AccumDepr (Accumulated Depreciation)
				//	8 => OtherAssets (Other Assets) 
				case 0: case 1: case 2: case 3: case 4: case 5: case 6: case 8:
					myAccount.setAccountTypesGuid(AccountType.ASSETS_TYPE_GUID);
					break;
				//	10 => Payable (Accounts Payable) 
				//	11 => Payables Retainage (Sage 50 Quantum Accounting only)
				//	12 => OthCurLiability (Other Current Liabilities)
				//	14 => OthLiability (Long Term Liabilities)
				case 10: case 11: case 12: case 14:
					myAccount.setAccountTypesGuid(AccountType.LIABILTIES_TYPE_GUID);
					break;
				//	21 => Income
				case 21:
					myAccount.setAccountTypesGuid(AccountType.REVENUE_TYPE_GUID);
					break;
				//	23 => CostSales (Cost of Sales)
				//	24 => Expense 
				case 23: case 24:
					myAccount.setAccountTypesGuid(AccountType.EXPENSES_TYPE_GUID);
					break;						
				//	16 => EquityNoClose (Equity - Doesn't Close),
				case 16:
					myAccount.setAccountTypesGuid(AccountType.PURPETUAL_EQUITY_TYPE_GUID);
					break;
				//	18 => RetEarn (Equity - Retained Earnings
				case 18:
					myAccount.setAccountTypesGuid(AccountType.RETAINED_TYPE_GUID);
					break;
				//	19 => EquityClose (Equity - Gets Closed))
				case 19:
					myAccount.setAccountTypesGuid(AccountType.EQUITY_TYPE_GUID);
					break;
				//	7 => Unused2 (Do not select)
				//	9 => Unused4 (Do not select)
				//	11 => Unused5 (Do not select) OR
				//	13 => Unused6 (Do not select)
				//	15 => Unused7 (Do not select) 
				//	17 => Unused8 (Do not select)
				//	20 => Unused9 (Do not select) 
				//	22 => Unused10 (Do not select) 
				//	3 => Unused1 (Do not select) OR
				default: throw new Exception("AccountType " + iAccountType + " is unknown!");
			}
			
			lstMyAccounts.add(myAccount);
		}
		
		myAdapter.save(Account.TABLE_NAME, lstMyAccounts, true);
	}
	private void importCustomers() throws Exception {}
}
