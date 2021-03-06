package net.internetworkconsulting.accounting.entities;

import java.util.List;
import net.internetworkconsulting.accounting.data.TransactionTypesRow;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.mysql.Statement;

public class TransactionType extends TransactionTypesRow {
	public static String TRANSACTION_GUID = "1d648ca26c9c40c59e1483aa262656c2";

	public static List<Option> loadOptions(AdapterInterface adapter) throws Exception {
		Statement stmt = new Statement(adapter.getSession().readJar(TransactionType.class, "TransactionType.loadOptions.sql"));		
		List<Option> lst = adapter.load(Option.class, stmt, true);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		return lst;
	}
	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
	}

	public String getRootUrl() {
		String sUrl = getEditUrl();
		if(sUrl.startsWith("/"))
			sUrl = "~/" + sUrl;
		else
			sUrl = "~";
		
		return sUrl;
	}
}
