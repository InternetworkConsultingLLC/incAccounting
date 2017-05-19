package net.internetworkconsulting.accounting.entities;

import java.util.List;
import net.internetworkconsulting.accounting.data.TimeEntryTypesRow;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.mysql.Statement;

public class TimeEntryType extends TimeEntryTypesRow {
	public static String INCOMPLETE = "d99313c888db4f71bd45c43cd09b492a";
	
	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
	}
	
	private static List<Option> lstOptions = null;
	public static List<Option> loadOptions(AdapterInterface adapter, boolean force) throws Exception {
		if(lstOptions != null && !force)
			return lstOptions;
		
		Statement stmt = new Statement(adapter.getSession().readJar(Document.class, "TimeEntryType.loadOptions.sql"));		
		List<Option> lst = adapter.load(Option.class, stmt, true);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		lstOptions = lst;
		return lst;
	}
}
