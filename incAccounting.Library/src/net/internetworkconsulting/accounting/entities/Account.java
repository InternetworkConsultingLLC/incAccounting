package net.internetworkconsulting.accounting.entities;

import java.math.BigDecimal;
import java.util.List;
import net.internetworkconsulting.accounting.data.AccountsRow;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.Row;
import net.internetworkconsulting.data.mysql.Statement;

public class Account extends AccountsRow {	
	public static String SETTING_SEPERATOR_SYMBOL = "Segment Seperator Symbol";
	public static String SETTING_SEPERATOR_ENABLED = "Segment Seperator Enabled";

	static String getSeperator(AdapterInterface adapter) throws Exception {
		String sUseSeperator = adapter.getSession().getSetting(Account.SETTING_SEPERATOR_ENABLED);
		String sSeperator = adapter.getSession().getSetting(Account.SETTING_SEPERATOR_SYMBOL);
		if(sSeperator == null || sUseSeperator == null)
			throw new Exception("Could not find setting '" + Account.SETTING_SEPERATOR_SYMBOL + "' or '" + Account.SETTING_SEPERATOR_ENABLED + "'!");
		
		boolean bUseSeperator = sUseSeperator.length() > 0 && (
			sUseSeperator.toLowerCase().charAt(0) == 'y' || sUseSeperator.toLowerCase().charAt(0) == 't'
		);
		if(!bUseSeperator)
			sSeperator = "";
		
		return sSeperator;
	}
	
	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
		this.setIsAllowed(true);		
	}
	
	public static BigDecimal getBeginningBalance(AdapterInterface adapter, String account_guid, String as_of_date) throws Exception {
		Statement stmt = new Statement(adapter.getSession().readJar(Account.class, "Account.getBeginningBalance.sql"));
		stmt.getParameters().put("{GUID}", account_guid);
		stmt.getParameters().put("{As Of Date}", as_of_date);
		List<Row> lstRows = adapter.load(Row.class, stmt, true);
		if(lstRows.size() != 1)
			return BigDecimal.ZERO;
		
		return (BigDecimal) lstRows.get(0).get("Value");
	}

	public static List<Option> loadOptions(AdapterInterface adapter) throws Exception {
		Statement stmt = new Statement(adapter.getSession().readJar(Account.class, "Account.loadOptions.sql"));		
		List<Option> lst = adapter.load(Option.class, stmt, true);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		return lst;
	}

	boolean bNumberChangedAndNotNew = false;
	public void beforeSave(AdapterInterface adapter) throws Exception {
		// construct account number in the form of:
		// parent.segment +  "-" + ... +  "-" parent.segment +  "-" + this.segment
		String sSeperator = Account.getSeperator(adapter);
			
		Account parent;
		if(this.getParentAccountsGuid() == null)
			parent = null;
		else
			parent = this.loadParentAccount(adapter, Account.class, false);
		
		// loop to root
		String sNumber = getSegment();
		String sName = getName();
		while(parent != null) {
			sNumber = parent.getSegment() + sSeperator + sNumber;
			sName = parent.getName() + " - " + sName;
			if(parent.getParentAccountsGuid() == null)
				parent = null;
			else
				parent = parent.loadParentAccount(adapter, Account.class, false);
		}
		
		// set computed number
		this.setNumber(sNumber);
		this.setNestedName(sName);
	}
	public void afterSave(AdapterInterface adapter) throws Exception {
		List<Account> lstAccounts = loadChildren(adapter, Account.class, false);
		for(Account accnt: lstAccounts)
			accnt.beforeSave(adapter);
		adapter.save(Account.TABLE_NAME, lstAccounts);
	}
}
