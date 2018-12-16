package net.internetworkconsulting.accounting.entities;

import java.util.HashMap;
import java.util.List;
import net.internetworkconsulting.accounting.data.PayrollFieldsRow;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.mysql.Statement;

public class PayrollField extends PayrollFieldsRow {
	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
	}
	
	
	public static List<Option> loadOptions(AdapterInterface adapter) throws Exception {
		Statement stmt = new Statement(adapter.getSession().readJar(PayrollField.class, "PayrollField.loadOptions.sql"));		
		List<Option> lst = adapter.load(Option.class, stmt, true);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		return lst;
	}
	public static List<Option> loadOptionsByType(AdapterInterface adapter, String type_guid) throws Exception {
		Statement stmt = new Statement(adapter.getSession().readJar(PayrollField.class, "PayrollField.loadOptionsByType.sql"));		
		stmt.getParameters().put("{Type GUID}", type_guid);
		List<Option> lst = adapter.load(Option.class, stmt, true);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		return lst;
	}
}
