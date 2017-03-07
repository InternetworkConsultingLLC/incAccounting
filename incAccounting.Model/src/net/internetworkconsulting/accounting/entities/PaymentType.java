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
import net.internetworkconsulting.accounting.data.PaymentTypesRow;
import net.internetworkconsulting.bootstrap.entities.Option;
import net.internetworkconsulting.bootstrap.entities.User;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.Row;
import net.internetworkconsulting.data.mysql.Statement;

public class PaymentType extends PaymentTypesRow { 
	private static List<Option> lstOptions;
	public static String SALES_PAYMENT_GUID = "2c12d6167d654604be3f533c38f1ad1e";
	public static String PURCHASE_PAYMENT_GUID = "a714a873202f4f12bb29a42ed8ed9b5c";
	
	public static List<Option> loadOptions(AdapterInterface adapter, boolean force) throws Exception {
		if(lstOptions != null && !force)
			return lstOptions;

		Statement stmt = new Statement(adapter.getSession().readJar(PaymentType.class, "PaymentType.loadOptions.sql"));
		List<Option> lst = adapter.load(Option.class, stmt);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		lstOptions = lst;
		return lst;
	}

	public static List loadSearch(AdapterInterface adapter, List<String> columns, String search) throws Exception {
		return Row.loadSearch(adapter, ContactType.class, columns, search);
	}
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
	@Override
	public void beforeSave(AdapterInterface adapter) throws Exception {
		if(getRowState() == RowState.Delete)
			throw new Exception("You may not delete payment types!");
	}
	
	
}
