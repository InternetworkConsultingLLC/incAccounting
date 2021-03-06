package net.internetworkconsulting.accounting.entities;

import java.util.List;
import net.internetworkconsulting.accounting.data.AccountTypesRow;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.mysql.Statement;

public class AccountType extends AccountTypesRow {
	public static String ASSETS_TYPE_GUID = "cf190cadde734f98a348d5f6bd112db6";
	public static String LIABILTIES_TYPE_GUID = "ddc9a0f637b64af2adeb19d0f6399e19";
	public static String RETAINED_TYPE_GUID = "6ee39c8c894e45fbb7892175e3365a34";
	public static String PURPETUAL_EQUITY_TYPE_GUID = "40f7e36772f240999fca4a9d2c1b536f";
	public static String EQUITY_TYPE_GUID = "531c81d9f0c942aeb5b742160d6b3942";
	public static String REVENUE_TYPE_GUID = "2ddbad1cf8d04f0d9c44fa9771a15306";
	public static String EXPENSES_TYPE_GUID = "ade6405dd31d40169b7ed222ecaa6b9e";

	public static List<Option> loadOptions(AdapterInterface adapter) throws Exception {
		Statement stmt = new Statement(adapter.getSession().readJar(AccountType.class, "AccountType.loadOptions.sql"));		
		List<Option> lst = adapter.load(Option.class, stmt, true);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		return lst;
	}
	
}
