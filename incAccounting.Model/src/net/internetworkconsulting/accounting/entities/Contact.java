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

import java.sql.Date;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import net.internetworkconsulting.accounting.data.ContactsRow;
import net.internetworkconsulting.bootstrap.entities.Option;
import net.internetworkconsulting.bootstrap.entities.User;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.Row;
import net.internetworkconsulting.data.mysql.Statement;

public class Contact extends ContactsRow {
	public static String OUR_COMPANY_GUID = "fabca02484aa46eaa24c939c64779a2d";

	private Object lstChildrenContactsChildren = null;
	public <T extends ContactsRow> List<T> loadChildrenContacts(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstChildrenContactsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Contacts\" WHERE \"Parent Contacts GUID\"={PRIMARYKEY} ORDER BY \"Display Name\"");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstChildrenContactsChildren = adapter.load(model, stmt);
		}
		return (List<T>) lstChildrenContactsChildren;
	}

	private static List<Option> lstOptions;
	public static List<Option> loadOptions(AdapterInterface adapter, boolean force) throws Exception {
		if(lstOptions != null && !force)
			return lstOptions;
		
		Statement stmt = new Statement(adapter.getSession().readFile("sql/Contact.loadOptions.sql"));		
		List<Option> lst = adapter.load(Option.class, stmt);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		lstOptions = lst;		
		return lst;
	}
	public static List loadSearch(AdapterInterface adapter, List<String> columns, String search) throws Exception { return Row.loadSearch(adapter, Contact.class, columns, search); }
	public static List getSearchColumns() {
		LinkedList<String> lstColumns = new LinkedList<>();
		//lstColumns.add("Parent Display Name");
		lstColumns.add(Contact.DISPLAY_NAME);
		//lstColumns.add(Contact.CONTACT_SINCE);
		//lstColumns.add("Mailiing Postal Code");
		//lstColumns.add("Shipping Postal Code");
		//lstColumns.add(Contact.WEBSITE);
		//lstColumns.add(Contact.EMAIL_ADDRESS);
		lstColumns.add(Contact.OFFICE_PHONE);
		lstColumns.add(Contact.MOBILE_PHONE);
		//lstColumns.add(Contact.HOME_PHONE);
		//lstColumns.add(Contact.FAX_NUMBER);
		return lstColumns;
	}

	private List<Option> lstChildOptions;
	public List<Option> loadChildOptions(AdapterInterface adapter, boolean force) throws Exception {
		if(lstChildOptions != null && !force)
			return lstChildOptions;
		
		Statement stmt = new Statement(adapter.getSession().readFile("sql/Contact.loadChildOptions.sql"));		
		stmt.getParameters().put("{GUID}", this.getGuid());
		List<Option> lst = adapter.load(Option.class, stmt);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		lstChildOptions = lst;		
		return lst;
	}

	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
		this.setContactSince(new Date(Instant.now().toEpochMilli()));
		this.setIsAllowed(true);
	}
	public void initialize(Contact parent) throws Exception {
		this.initialize();
		this.setParentContactsGuid(parent.getGuid());
		this.setCity(parent.getCity());
		this.setCountry(parent.getCountry());
		this.setLine1(parent.getLine1());
		this.setLine2(parent.getLine2());
		this.setPostalCode(parent.getPostalCode());
		this.setState(parent.getState());
		this.setTaxGroupGuid(parent.getTaxGroupGuid());
	}
}
