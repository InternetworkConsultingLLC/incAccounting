package net.internetworkconsulting.accounting.entities;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.mysql.Statement;

public class Setting extends net.internetworkconsulting.accounting.data.SettingsRow {
	public static String TYPE_BOOLEAN = "Boolean";
	public static String TYPE_GUID = "Guid";
	public static String TYPE_NUMBER = "Number";
	public static String TYPE_STRING = "String";

	public Setting() { super(); }
	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
	}
	
	public static <T extends Setting> T loadByKey(AdapterInterface adapter, Class model, String value) throws Exception {
		String sql = "SELECT * FROM \"Settings\" WHERE \"" + Setting.KEY + "\"={VALUE}";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{VALUE}", value);

		List<T> lst = adapter.load(model, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique settings row by 'GUID': " + Statement.convertObjectToString(value, null));

		return lst.get(0);
	}

}
