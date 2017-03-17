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
package net.internetworkconsulting.accounting.entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.mysql.Statement;

public class Setup implements Serializable {
	public static String SQL_SERVER = "SQL Server";
	private String sSqlServer = "";
	public String getSqlServer() {
		return sSqlServer;
	}
	public void setSqlServer(String value) {
		sSqlServer = value;
	}

	public static String DATABASE = "Database";
	private String sDatabase = "";
	public String getDatabase() {
		return sDatabase;
	}
	public void setDatabase(String value) {
		sDatabase = value;
	}

	public static String SQL_USER = "SQL User";
	private String sSqlUser = "";
	public String getSqlUser() {
		return sSqlUser;
	}
	public void setSqlUser(String value) {
		sSqlUser = value;
	}

	public static String PASSWORD = "Password";
	private String sPassword = "";
	public String getPassword() {
		return sPassword;
	}
	public void setPassword(String value) {
		sPassword = value;
	}

	public static String ADMINISTRATOR_PASSWORD = "Administrator Password";
	private String sAdministratorPassword = "";
	public String getAdministratorPassword() {
		return sAdministratorPassword;
	}
	public void setAdministratorPassword(String value) {
		sAdministratorPassword = value;
	}

	protected AdapterInterface connect() throws Exception {
		User user = new User();
		user.setDatabase(getDatabase());
		return user.unvalidatedLogin();
	}
	public void testConnection() throws Exception {
		AdapterInterface adapter = connect();
	}
	public void dropDatabase() throws Exception {
		Statement stmt = new Statement("DROP DATABASE \"%DATABASE%\";");
		stmt.setCommand(stmt.getCommand().replace("%DATABASE%", getDatabase()));

		connect().execute(stmt, false);
	}

	public List<String> getScripts() {
		List<String> lstSql = new LinkedList<>();

		lstSql.add("20151007.Database.sql");
		lstSql.add("20170306.StockReports.sql");

		return lstSql;
	}
	
	public void createDatabase(List<String> lstFiles) throws Exception {
		List<String> lstSql = new LinkedList<>();
		for(String file : lstFiles)
			for(String line : User.readJarFile(Setup.class, file).split("\\;[\\s^\\n]*\\n"))
				lstSql.add(line);
		
		AdapterInterface adapter = connect();
		for(String sql: lstSql) {
			if(sql.trim().length() > 0) {
				Statement stmt = new Statement(sql);
				stmt.setCommand(stmt.getCommand().replace("%DATABASE%", getDatabase()));
				try {
					adapter.execute(stmt, false);
				}
				catch(Exception ex) {
					throw new Exception(ex.getMessage() + "\n\n" + stmt.generate(null, false));
				}
			}
		}
	}
	public void createAdministrator() throws Exception {
		AdapterInterface adapter = connect();
		adapter.execute(new Statement("USE \"" + getDatabase() + "\""), false);

		User user = User.loadByGuid(adapter, User.class, User.ADMINISTRATOR_GUID);
		user.setDatabase(getDatabase());
		user.setPasswordHash(User.hashPassword(getAdministratorPassword()));		
		user.setPasswordDate(new java.sql.Date(java.time.Instant.EPOCH.toEpochMilli()));
		
		//adapter.save(User.TABLE_NAME, this);
	}

}
