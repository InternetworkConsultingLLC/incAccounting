package net.internetworkconsulting.accounting.entities;

import java.util.HashMap;
import java.util.List;
import net.internetworkconsulting.accounting.data.SecurablesRow;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.mysql.Statement;

public class Securable extends net.internetworkconsulting.accounting.data.SecurablesRow {
	public Securable() { super(); }
	
	private HashMap<String, Permission> hmGroupsPermissions = new HashMap<>();
	public Permission loadPerimissionsForGroup(AdapterInterface adapter, boolean forced, String group_guid) throws Exception {
		if(!forced && hmGroupsPermissions.containsKey(group_guid))
			return hmGroupsPermissions.get(group_guid);
		
		Statement stmt = new Statement(adapter.getSession().readJar(SalesTax.class, "Securable.loadPerimissionsForGroup.sql"));
		stmt.getParameters().put("@GroupsGuid", group_guid);
		stmt.getParameters().put("@SecurablesGuid", getGuid());
		List<Permission> lstPerms = adapter.load(Permission.class, stmt, true);

		Permission obj = null;
		if(lstPerms.size() == 1) {
			obj = lstPerms.get(0);
		} else {
			obj = new Permission();
			obj.initialize();
			obj.setGroupsGuid(group_guid);
			obj.setSecurablesGuid(getGuid());
			
			if(group_guid.equals(Group.ADMINISTRATORS_GUID)) {
				obj.setCanCreate(true);
				obj.setCanRead(true);
				obj.setCanUpdate(true);
				obj.setCanDelete(true);
			}
		}
		
		hmGroupsPermissions.put(group_guid, obj);
		return obj;
	}
	
	public static <T extends SecurablesRow> List<T> loadAll(AdapterInterface adapter, Class model) throws Exception {
		Statement stmt = new Statement("SELECT * FROM \"Securables\" ORDER BY \"Display Name\"");
		return (List<T>) adapter.load(model, stmt, true);
	}
	
	public String generateSqlInserts(AdapterInterface adapter) throws Exception {
		String sql = "";
		sql += "INSERT INTO \"" + TABLE_NAME + "\" (";
		sql += " \"" + DISPLAY_NAME + "\", ";
		sql += " \"" + GUID + "\" ) VALUES (";
		sql += Statement.convertObjectToSql(this.getDisplayName()) + ", ";
		sql += Statement.convertObjectToSql(this.getGuid()) + ");\n";
		
		return sql;
	}	
	public String generateSqlDeletes(AdapterInterface adapter) throws Exception {
		String sql = "";
		sql += "DELETE FROM \"" + TABLE_NAME + "\" WHERE \"" + GUID + "\"=" + Statement.convertObjectToSql(this.getGuid()) + ";\n";
		return sql;
	}	
}
