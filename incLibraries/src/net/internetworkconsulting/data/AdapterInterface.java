package net.internetworkconsulting.data;

import net.internetworkconsulting.data.mysql.Statement;
import java.util.HashMap;
import java.util.List;

public interface AdapterInterface {
	java.sql.Connection getConnection();

	long begin(boolean is_final) throws Exception;
	long rollback(boolean is_final) throws Exception;
	long commit(boolean is_final) throws Exception;

	boolean execute(Statement stmt, boolean log_query) throws Exception;
	<R extends RowInterface> List<R> load(Class<R> cls, StatementInterface stmt, boolean log_query) throws Exception;
	<R extends RowInterface> long save(String table_name, List<R> table, boolean before_and_after) throws Exception;
	<R extends RowInterface> long save(String table_name, R row, boolean before_and_after) throws Exception;
	<R extends RowInterface> long save(String table_name, List<R> table) throws Exception;
	<R extends RowInterface> long save(String table_name, R row) throws Exception;

	<R extends RowInterface> HashMap<String, String> getColumns(Class<R> cls, StatementInterface stmt) throws Exception;
		
	void setSession(SessionInterface session);
	SessionInterface getSession();
}
