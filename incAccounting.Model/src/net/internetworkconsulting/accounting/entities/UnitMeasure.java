package net.internetworkconsulting.accounting.entities;

import java.util.List;
import net.internetworkconsulting.accounting.data.UnitMeasuresRow;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.mysql.Statement;

public class UnitMeasure extends UnitMeasuresRow {
	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
		this.setIsAllowed(true);		
	}
	
	private static List<Option> lstOptions;
	public static List<Option> loadOptions(AdapterInterface adapter, boolean force) throws Exception {
		if(lstOptions != null && !force)
			return lstOptions;
		
		Statement stmt = new Statement(adapter.getSession().readJar(UnitMeasure.class, "UnitMeasure.loadOptions.sql"));		
		List<Option> lst = adapter.load(Option.class, stmt, true);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		lstOptions = lst;		
		return lst;
	}
}
