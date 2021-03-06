package net.internetworkconsulting.accounting.entities;

import java.util.List;
import net.internetworkconsulting.accounting.data.PayrollFieldTypesRow;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.mysql.Statement;

public class PayrollFieldType extends PayrollFieldTypesRow {
	public static String TYPE_GROSS_EXPENSE_GUID = "af32731792b64a6081a6a1f73d9afca0";
	public static String TYPE_COMPANY_PAID_GUID = "2f542318ae174eaf8bb95f02ed8f6df5";
	public static String TYPE_EMPLOYEE_PAID_GUID = "f1b0d26375e74b0eb1aeb53bace00499";
	
	public static List<Option> loadOptions(AdapterInterface adapter) throws Exception {
		Statement stmt = new Statement(adapter.getSession().readJar(PayrollFieldType.class, "PayrollFieldType.loadOptions.sql"));
		List<Option> lst = adapter.load(Option.class, stmt, true);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		return lst;
	}	
}
