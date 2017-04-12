package net.internetworkconsulting.data.pervasive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.RowInterface;
import net.internetworkconsulting.data.SessionInterface;
import net.internetworkconsulting.data.StatementInterface;
import net.internetworkconsulting.data.mysql.Statement;

public class Adapter implements AdapterInterface {
	
	public Adapter(String user, String password, String server, String database) throws Exception {
		String url = "jdbc:pervasive://" + server + ":1583/" + database + ";encoding=cp850";
		Class.forName("com.pervasive.jdbc.v2.Driver");
		ptConnection = DriverManager.getConnection(url, user, password);
	}
	protected void finalize() throws Exception, Throwable {
		super.finalize();

		if(ptConnection == null)
			return;

		ptConnection.close();
		ptConnection = null;
	}
	
	private Connection ptConnection = null;
	public Connection getConnection() { return ptConnection; }

	public long begin(boolean is_final) throws Exception { throw new UnsupportedOperationException("Not supported yet."); }
	public long rollback(boolean is_final) throws Exception { throw new UnsupportedOperationException("Not supported yet."); }
	public long commit(boolean is_final) throws Exception { throw new UnsupportedOperationException("Not supported yet."); }

	public boolean execute(Statement stmt, boolean log_query) throws Exception { 
		return ptConnection.createStatement().execute(stmt.generate(getSession(), false));
	}
	public <R extends RowInterface> List<R> load(Class<R> cls, StatementInterface stmt, boolean log_query) throws Exception {
		java.sql.ResultSet rs = ptConnection.createStatement().executeQuery(stmt.generate(getSession(), false));

		List<R> newTable = new java.util.LinkedList<>();

		HashMap<String, String> mapColumns = new HashMap<>();
		for(int cnt = 0; cnt < rs.getMetaData().getColumnCount(); cnt++) {
			String sColumnName = rs.getMetaData().getColumnLabel(cnt + 1);
			String sColumnType = rs.getMetaData().getColumnTypeName(cnt + 1);
			mapColumns.put(sColumnName, sColumnType);
		}

		while(rs.next()) {
			java.util.HashMap<String, Object> map = new java.util.HashMap<>();
			for(int cnt = 0; cnt < rs.getMetaData().getColumnCount(); cnt++) {
				Object obj = rs.getObject(cnt + 1);
				String col = rs.getMetaData().getColumnLabel(cnt + 1);
				map.put(col, obj);
			}

			R row = cls.newInstance();
			row.setColumns(mapColumns);
			row.setOriginals(map);
			newTable.add(row);
		}

		return newTable;
	}

	public <R extends RowInterface> long save(String table_name, List<R> table, boolean before_and_after) throws Exception { throw new UnsupportedOperationException("Not supported yet."); }
	public <R extends RowInterface> long save(String table_name, R row, boolean before_and_after) throws Exception { throw new UnsupportedOperationException("Not supported yet."); }
	public <R extends RowInterface> long save(String table_name, List<R> table) throws Exception { throw new UnsupportedOperationException("Not supported yet."); }
	public <R extends RowInterface> long save(String table_name, R row) throws Exception { throw new UnsupportedOperationException("Not supported yet."); }

	public <R extends RowInterface> HashMap<String, String> getColumns(Class<R> cls, StatementInterface stmt) throws Exception { throw new UnsupportedOperationException("Not supported yet."); }

	public void setSession(SessionInterface session) { throw new UnsupportedOperationException("Not supported yet."); }
	public SessionInterface getSession() { return null; }
}
