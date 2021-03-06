package net.internetworkconsulting.accounting.entities;

import java.util.Date;
import java.util.List;
import net.internetworkconsulting.accounting.data.EmployeesRow;
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
		
//		Date dtNever = new Date(Long.MAX_VALUE);
//		
//		this.setDateHirred(dtNever);
//		this.setDateOfBirth(dtNever);
//		this.setDateTerminated(dtNever);
//		this.setDateVerified(dtNever);
	}
	
	public static List<Option> loadOptions(AdapterInterface adapter) throws Exception {
		Statement stmt = new Statement(adapter.getSession().readJar(Employee.class, "Employee.loadOptions.sql"));		
		List<Option> lst = adapter.load(Option.class, stmt, true);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		return lst;
	}
}
