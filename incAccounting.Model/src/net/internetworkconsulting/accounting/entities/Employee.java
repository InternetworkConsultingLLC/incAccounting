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
import java.util.List;
import net.internetworkconsulting.accounting.data.EmployeesRow;
import net.internetworkconsulting.bootstrap.entities.Option;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.mysql.Statement;

public class Employee extends EmployeesRow {
	public void initialize() throws Exception {
		super.initialize();
	}	
	public void initialize(Contact contact) throws Exception {
		this.setGuid(contact.getGuid());
		
		if(contact.getRowState() == RowState.Insert) {
			contact.setParentContactsGuid(Contact.OUR_COMPANY_GUID);
			contact.setContactTypesGuid(ContactType.TYPE_EMPLOYEE_GUID);
		}
		
		Date dtNever = new Date(Long.MAX_VALUE);
		
		this.setDateHirred(dtNever);
		this.setDateOfBirth(dtNever);
		this.setDateTerminated(dtNever);
		this.setDateVerified(dtNever);
	}
	
	private static List<Option> lstOptions;
	public static List<Option> loadOptions(AdapterInterface adapter, boolean force) throws Exception {
		if(lstOptions != null && !force)
			return lstOptions;
		
		Statement stmt = new Statement(adapter.getSession().readJar(Employee.class, "Employee.loadOptions.sql"));		
		List<Option> lst = adapter.load(Option.class, stmt);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		lstOptions = lst;		
		return lst;
	}
}
