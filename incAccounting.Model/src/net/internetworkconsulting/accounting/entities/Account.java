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

import java.util.LinkedList;
import java.util.List;
import net.internetworkconsulting.accounting.data.AccountsRow;
import net.internetworkconsulting.bootstrap.entities.Option;
import net.internetworkconsulting.bootstrap.entities.User;
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
	
	private static List<Option> lstOptions;
	public static List<Option> loadOptions(AdapterInterface adapter, boolean force) throws Exception {
		if(lstOptions != null && !force)
			return lstOptions;
		
		Statement stmt = new Statement(adapter.getSession().readJar(Account.class, "Account.loadOptions.sql"));		
		List<Option> lst = adapter.load(Option.class, stmt);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		lstOptions = lst;		
		return lst;
	}
	public static List loadSearch(AdapterInterface adapter, List<String> columns, String search) throws Exception { return Row.loadSearch(adapter, Account.class, columns, search); }
	public static List getSearchColumns() {
		LinkedList<String> lstColumns = new LinkedList<>();
		lstColumns.add(Account.NUMBER);
		lstColumns.add(Account.NAME);
		lstColumns.add(Account.IS_ALLOWED);
		return lstColumns;
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
