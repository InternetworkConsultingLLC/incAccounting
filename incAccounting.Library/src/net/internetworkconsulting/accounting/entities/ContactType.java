package net.internetworkconsulting.accounting.entities;

import java.util.List;
import net.internetworkconsulting.accounting.data.ContactTypesRow;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.mysql.Statement;

public class ContactType extends ContactTypesRow {
	public static String TYPE_BUSINESS_GUID = "4134430d7bb64a97b95f7862bea83644";
	public static String TYPE_EMPLOYEE_GUID = "7c85de861e784b9f8dcb4a456e267869";
	public static String TYPE_INDIVIDUAL_GUID = "77c1c5bad818403f8ae836a3fc7fd84b";

	public static List<Option> loadOptions(AdapterInterface adapter) throws Exception {
		Statement stmt = new Statement(adapter.getSession().readJar(ContactType.class, "ContactType.loadOptions.sql"));		
		List<Option> lst = adapter.load(Option.class, stmt, true);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		return lst;
	}

	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
		this.setIsAllowed(true);
	}
}
