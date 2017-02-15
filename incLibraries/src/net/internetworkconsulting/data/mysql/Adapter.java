/*
 * Copyright (C) 2016 Internetwork Consulting LLC
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see http://www.gnu.org/licenses/.
 */
package net.internetworkconsulting.data.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.RowInterface;
import net.internetworkconsulting.data.SessionInterface;
import net.internetworkconsulting.data.StatementInterface;

public class Adapter implements AdapterInterface {

	public Connection getConnection() {
		return myConnection;
	}
	private java.sql.Connection myConnection = null;
	private boolean bIsConnectionMine = false;
	private static String SET_SESSION = "SET SESSION sql_mode = 'ANSI_QUOTES,NO_ZERO_DATE,STRICT_ALL_TABLES,TRADITIONAL;";

	public Adapter(String server, String database, String user, String password, boolean ssl) throws Exception {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		String sUri = "jdbc:mysql://" + server + "/" + database + "?zeroDateTimeBehavior=convertToNull";
		if(ssl)
			sUri = sUri + "&verifyServerCertificate=false&useSSL=true&requireSSL=true";
		myConnection = DriverManager.getConnection(sUri, user, password);
		bIsConnectionMine = true;
		prepareConnection();
	}
	public Adapter(java.sql.Connection connnection) throws Exception {
		myConnection = connnection;
		myConnection.createStatement().execute(SET_SESSION);
		bIsConnectionMine = false;
	}
	public Adapter(AdapterInterface ai) throws Exception {
		myConnection = ai.getConnection();
		myConnection.createStatement().execute(SET_SESSION);
		bIsConnectionMine = false;
	}

	protected void finalize() throws Exception, Throwable {
		super.finalize();
		
		if(!bIsConnectionMine || myConnection == null)
			return;

		myConnection.close();
		myConnection = null;
	}
	private void prepareConnection() throws Exception {
		myConnection.createStatement().execute("SET SESSION sql_mode = 'ANSI_QUOTES,NO_ZERO_DATE';");
	}

	public boolean execute(Statement stmt, boolean log_query) throws Exception {
		return myConnection.createStatement().execute(stmt.generate(getSession(), log_query));
	}
	public HashMap<String, String> loadColumns(String table) throws Exception {
		Statement stmt = new Statement("SELECT * FROM \"" + table + "\" WHERE 1<>1");
		java.sql.ResultSet rs = myConnection.createStatement().executeQuery(stmt.generate(getSession(), false));

		HashMap<String, String> mapColumns = new HashMap<>();
		for(int cnt = 0; cnt < rs.getMetaData().getColumnCount(); cnt++) {
			String sColumnName = rs.getMetaData().getColumnName(cnt + 1);
			String sColumnType = rs.getMetaData().getColumnTypeName(cnt + 1);
			mapColumns.put(sColumnName, sColumnType);
		}

		return mapColumns;
	}
	public <R extends RowInterface> List<R> load(Class<R> cls, StatementInterface stmt) throws Exception {
		if(myUser != null) {
			R ri = null;
			try { ri = cls.newInstance(); }
			catch(Exception ex) { throw new Exception("A call to 'load()' threw an exception.  This is probably because the 'class' supplied is not assignable to the specified variable (type miss-match).", ex); }
			myUser.canRead(ri.getSqlSecurableGuid());
		}

		java.sql.ResultSet rs = myConnection.createStatement().executeQuery(stmt.generate(getSession(), false));

		List<R> newTable = new java.util.LinkedList<>();

		mapColumns = new HashMap<>();
		for(int cnt = 0; cnt < rs.getMetaData().getColumnCount(); cnt++) {
			String sColumnName = rs.getMetaData().getColumnLabel(cnt + 1);
			String sColumnType = rs.getMetaData().getColumnTypeName(cnt + 1);
			mapColumns.put(sColumnName, sColumnType);
		}

		rs.beforeFirst();
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
	public <R extends RowInterface> long save(String table_name, List<R> table, boolean before_and_after) throws Exception {
		try {
			begin(false);

			long retChanges = 0;
			java.util.LinkedList<java.lang.Long> lstRemoves = new java.util.LinkedList<>();
			
			HashMap<String, String> hmColumns = loadColumns(table_name);
			
			for(long cnt = 0; cnt < table.size(); cnt++) {
				R row = table.get((int) cnt);
				StatementInterface stmt;
				if(before_and_after)
					row.beforeSave(this);

				switch(row.getRowState()) {
					case Insert:
						if(myUser != null)
							myUser.canCreate(row.getSqlSecurableGuid());
						stmt = Statement.createInsert(row, hmColumns);
						myConnection.createStatement().executeUpdate(stmt.generate(getSession(), true));
						if(before_and_after)
							row.afterSave(this);
						retChanges++;
						break;
					case Update:
						if(myUser != null)
							myUser.canUpdate(row.getSqlSecurableGuid());
						stmt = Statement.createUpdate(row, hmColumns);
						myConnection.createStatement().executeUpdate(stmt.generate(getSession(), true));
						if(before_and_after)
							row.afterSave(this);
						retChanges++;
						break;
					case Delete:
						if(myUser != null)
							myUser.canDelete(row.getSqlSecurableGuid());
						stmt = Statement.createDelete(row, hmColumns);
						myConnection.createStatement().executeUpdate(stmt.generate(getSession(), true));
						if(before_and_after)
							row.afterSave(this);
						retChanges++;
						break;
				}
			}

			commit(false);
			return retChanges;
		}
		catch(Exception ex) {
			rollback(false);
			throw ex;
		}
	}
	public <R extends RowInterface> long save(String table, R row, boolean before_and_after) throws Exception {
		List<R> lst = new LinkedList<>();
		lst.add(row);
		return this.save(table, lst, before_and_after);
	}
	public <R extends RowInterface> long save(String table_name, List<R> table) throws Exception {
		return save(table_name, table, true);
	}
	public <R extends RowInterface> long save(String table, R row) throws Exception {
		return save(table, row, true);
	}

	private static HashMap<String, String> mapColumns;
	public <R extends RowInterface> HashMap<String, String> getColumns(Class<R> cls, StatementInterface stmt) throws Exception {
		String sql = "SELECT * FROM (" + stmt.getCommand() + ") TBL WHERE 1 <> 1";
		stmt.setCommand(sql);
		load(cls, stmt);
		return mapColumns;
	}

	private long lTransactions = 0;
	public long begin(boolean is_final) throws Exception {
		if(lTransactions < 0)
			throw new Exception("Transaction cannot be started as the transaction(s) were rolled back!");
		if(is_final && lTransactions != 0)
			throw new Exception("A transaction is open already!");
		if(lTransactions == 0)
			myConnection.createStatement().execute("BEGIN;");

		lTransactions++;
		return lTransactions;
	}
	public long commit(boolean is_final) throws Exception {
		if(lTransactions < 0)
			throw new Exception("Cannot commit as transactions have been rolled back!");

		if(is_final && lTransactions != 1)
			throw new Exception("Not all transaction begins had a commit: transaction failed!");
		if(lTransactions == 1)
			myConnection.createStatement().execute("COMMIT;");

		lTransactions--;
		return lTransactions;
	}
	public long rollback(boolean is_final) throws Exception {
		if(is_final) {
			myConnection.createStatement().execute("ROLLBACK;");
			lTransactions = 0;
			myConnection.close();
			myConnection = null;
		} else {
			if(lTransactions != Long.MIN_VALUE)
				myConnection.createStatement().execute("ROLLBACK;");

			lTransactions = Long.MIN_VALUE;
		}

		return lTransactions;
	}

	SessionInterface myUser = null;
	public void setSession(SessionInterface session) {
		myUser = session;
	}
	public SessionInterface getSession() {
		return myUser;
	}
}
