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

import java.util.List;
import net.internetworkconsulting.accounting.data.PayrollFieldTypesRow;
import net.internetworkconsulting.bootstrap.entities.Option;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.mysql.Statement;

public class PayrollFieldType extends PayrollFieldTypesRow {
	public static String TYPE_GROSS_EXPENSE_GUID = "af32731792b64a6081a6a1f73d9afca0";
	public static String TYPE_COMPANY_PAID_GUID = "2f542318ae174eaf8bb95f02ed8f6df5";
	public static String TYPE_EMPLOYEE_PAID_GUID = "f1b0d26375e74b0eb1aeb53bace00499";
	
	private static List<Option> lstOptions;
	public static List<Option> loadOptions(AdapterInterface adapter, boolean force) throws Exception {
		if(lstOptions != null && !force)
			return lstOptions;

		Statement stmt = new Statement(adapter.getSession().readJar(PayrollFieldType.class, "PayrollFieldType.loadOptions.sql"));
		List<Option> lst = adapter.load(Option.class, stmt);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		lstOptions = lst;
		return lst;
	}	
}
