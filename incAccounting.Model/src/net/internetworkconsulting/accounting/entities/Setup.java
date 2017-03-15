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
		user.setUserSalt(" ");
		user.setPasswordSalt(" ");
		user.setDatabase("mysql");
		user.setSqlServer(getSqlServer());
		user.setSqlUser(getSqlUser());
		user.setPassword(getPassword());

		return user.login(false);
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

		lstSql.add("20151007.Bootstrap.sql");
		lstSql.add("20151111.Accounting.sql");
		lstSql.add("20160204.01.Accounting.sql");
		lstSql.add("20160204.02.Data.sql");
		lstSql.add("20170222.FIFO.sql");
		lstSql.add("20170301.ReportFixes.sql");
		lstSql.add("20170306.StockReports.sql");
		lstSql.add("20170309.EditListURLs.sql");
		lstSql.add("20170310.AccountRegister.sql");

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

		List<Computer> lstComputers = createComputers();
		adapter.save(Computer.TABLE_NAME, lstComputers);
	}
	private List<Computer> createComputers() throws Exception {
//		String sMac = User.getMacAddress();
//
//		Computer comp = new Computer();
//		comp.setDescription("First Installed");
//		comp.setGuid(User.newGuid());
//		comp.setIsAllowed(true);
//		comp.setMacAddress(sMac);

		List<Computer> lstComputers = new LinkedList<>();
		//lstComputers.add(comp);
		return lstComputers;
	}
	public void createAdministrator() throws Exception {
		AdapterInterface adapter = connect();
		adapter.execute(new Statement("USE \"" + getDatabase() + "\""), false);
		User user = User.loadByGuid(adapter, User.class, User.ADMINISTRATOR_GUID);
		user.setDatabase(getDatabase());

		String sUser = user.getUserSalt().replace(" ", user.getSqlUser());
		String sPassword = user.getPasswordSalt().replace(" ", getAdministratorPassword());

		if(sUser.contains("'"))
			throw new Exception("The user name must not contain an apostrophe (')!");
		if(sPassword.contains("'"))
			throw new Exception("The password must not contain an apostrophe (')!");

		String sql;
		Statement stmt;

		try {
			sql = "DROP USER {USER}@'%';";
			stmt = new Statement(sql);
			stmt.getParameters().put("{USER}", sUser);
			adapter.execute(stmt, false);
		}
		catch(Exception ex) {
		}

		sql = "CREATE USER {USER}@'%' IDENTIFIED BY {PASSWORD} REQUIRE SSL;";
		stmt = new Statement(sql);
		stmt.getParameters().put("{USER}", sUser);
		stmt.getParameters().put("{PASSWORD}", sPassword);
		adapter.execute(stmt, false);

		sql = " GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE, SHOW VIEW ON \"DATABASE\".* TO {USER}@'%' WITH GRANT OPTION; \n";
		sql = sql.replace("DATABASE", getDatabase());
		stmt = new Statement(sql);
		stmt.getParameters().put("{USER}", sUser);
		adapter.execute(stmt, false);

		sql = " GRANT CREATE USER ON *.* TO {USER}@'%' WITH GRANT OPTION; ";
		stmt = new Statement(sql);
		stmt.getParameters().put("{USER}", sUser);
		adapter.execute(stmt, false);
	}

}
