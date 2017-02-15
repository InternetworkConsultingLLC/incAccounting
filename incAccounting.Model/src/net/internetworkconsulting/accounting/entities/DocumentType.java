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
import net.internetworkconsulting.accounting.data.DocumentTypesRow;
import net.internetworkconsulting.bootstrap.entities.Option;
import net.internetworkconsulting.bootstrap.entities.User;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.Row;
import net.internetworkconsulting.data.mysql.Statement;

public class DocumentType extends DocumentTypesRow {
	public static String SALES_QUOTE_GUID = "276db4afcf634b6fbc4a5821c9858ab9";
	public static String SALES_ORDER_GUID = "5f756fc5f7c5493ca0d86f2d0ead2fda";
	public static String SALES_INVOICE_GUID = "81e2917ac5c34d1cb6f9d168cd0439db";
	public static String SALES_CREDIT_GUID = "86af180c412f40c5a660678e3895694b";
	public static String PURCHASE_QUOTE_GUID = "dedf79eddf7c4e348918d42e25b53309";
	public static String PURCHASE_ORDER_GUID = "e56b2b6aa42b479890085b74b69275f3";
	public static String PURCHASE_INVOICE_GUID = "9d3821afd6fb47f9b2713d3cc574ceff";
	public static String PURCHASE_CREDIT_GUID = "6632ec00f5824aeca4a49bf21cbdaece";

	private static List<Option> lstOptions;
	public static List<Option> loadOptions(AdapterInterface adapter, boolean force) throws Exception {
		if(lstOptions != null && !force)
			return lstOptions;

		Statement stmt = new Statement(adapter.getSession().readFile("sql/DocumentType.loadOptions.sql"));		
		List<Option> lst = adapter.load(Option.class, stmt);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		lstOptions = lst;
		return lst;
	}

	private static List<Option> lstPostableOptions;
	public static List<Option> loadPostableOptions(AdapterInterface adapter, boolean force) throws Exception {
		if(lstPostableOptions != null && !force)
			return lstPostableOptions;

		Statement stmt = new Statement(adapter.getSession().readFile("sql/DocumentType.loadPostableOptions.sql"));		
		List<Option> lst = adapter.load(Option.class, stmt);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		lstPostableOptions = lst;
		return lst;
	}

	
	public static List loadSearch(AdapterInterface adapter, List<String> columns, String search) throws Exception { return Row.loadSearch(adapter, ContactType.class, columns, search); }
	public static List getSearchColumns() {
		LinkedList<String> lstColumns = new LinkedList<>();
		lstColumns.add(TransactionType.NAME);
		lstColumns.add(TransactionType.IS_ALLOWED);
		return lstColumns;
	}
	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
	}
	
	public void setTransactionType(TransactionType value) {
		this.rTransactionTypeParent = value;
	}
	
}
