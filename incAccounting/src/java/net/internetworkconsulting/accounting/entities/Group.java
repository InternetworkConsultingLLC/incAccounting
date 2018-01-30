package net.internetworkconsulting.accounting.entities;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.mysql.Statement;

public class Group extends net.internetworkconsulting.accounting.data.GroupsRow {
	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
		this.setIsAllowed(true);
	}

	public static String ADMINISTRATORS_GUID = "f541b846c9224fc687420fce2a0ec8b1";
	public static String EVERYONE_GUID = "11eede08a5f34402a2547edc6aad2529";

	private static List<Option> lstOptions;
	public static List<Option> loadOptions(AdapterInterface adapter, boolean force) throws Exception {
		if(lstOptions != null && !force)
			return lstOptions;

		Statement stmt = new Statement(adapter.getSession().readJar(Group.class, "Group.loadOptions.sql"));
		List<Option> lst = adapter.load(Option.class, stmt, true);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		lstOptions = lst;
		return lst;
	}

	public Group() { super(); }

	private static List<Securable> lstSecurables;
	public static List<Securable> loadSecurables(AdapterInterface adapter, boolean forced) throws Exception {
		if(!forced && lstSecurables != null)
			return lstSecurables;
		
		lstSecurables = Securable.loadAll(adapter, Securable.class);
		return lstSecurables;
	}
}
