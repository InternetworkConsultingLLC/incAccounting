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
package net.internetworkconsulting.bootstrap.entities;

import java.io.Serializable;
import java.util.Calendar;
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

	public void createDatabase(String program_path) throws Exception {
		AdapterInterface adapter = connect();

		String[] arrSql = User.readJarFile(Setup.class, "Database.20151007.sql").split("\\;");
		for(String sql: arrSql)
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

		List<Computer> lstComputers = createComputers();
		List<Group> lstGroups = createGroups();
		List<Securable> lstSecurables = createSecurables();
		List<User> lstUsers = createUsers();
		List<Membership> lstMembership = createMemberships();
		List<Setting> lstSettings = createSettings();
		List<Permission> lstPermissions = createPermissions();

		createReport(adapter);

		adapter.save(Computer.TABLE_NAME, lstComputers);
		adapter.save(Group.TABLE_NAME, lstGroups);
		adapter.save(Securable.TABLE_NAME, lstSecurables);
		adapter.save(User.TABLE_NAME, lstUsers);
		adapter.save(Membership.TABLE_NAME, lstMembership);
		adapter.save(Setting.TABLE_NAME, lstSettings);
		adapter.save(Permission.TABLE_NAME, lstPermissions);
	}
	private List<Group> createGroups() throws Exception {
		LinkedList<Group> lstGroups = new LinkedList<Group>();
		Group grp;

		grp = new Group();
		grp.setGuid(Group.ADMINISTRATORS_GUID);
		grp.setDisplayName("System Administrators");
		grp.setEmailAddress("postmaster@localhost");
		grp.setIsAllowed(true);
		lstGroups.add(grp);

		grp = new Group();
		grp.setGuid(Group.EVERYONE_GUID);
		grp.setDisplayName("Everyone");
		grp.setEmailAddress("everyone@localhost");
		grp.setIsAllowed(true);
		lstGroups.add(grp);

		return lstGroups;
	}
	private List<Securable> createSecurables() throws Exception {
		LinkedList<Securable> lstSecurables = new LinkedList<>();
		Securable sec;

		sec = new Securable();
		sec.setDisplayName("Table - Computers");
		sec.setGuid((new Computer()).getSqlSecurableGuid());
		lstSecurables.add(sec);

		sec = new Securable();
		sec.setDisplayName("Table - Groups");
		sec.setGuid((new Group()).getSqlSecurableGuid());
		lstSecurables.add(sec);

		sec = new Securable();
		sec.setDisplayName("Table - Logs");
		sec.setGuid((new Log()).getSqlSecurableGuid());
		lstSecurables.add(sec);

		sec = new Securable();
		sec.setDisplayName("Table - Memberships");
		sec.setGuid((new Membership()).getSqlSecurableGuid());
		lstSecurables.add(sec);

		sec = new Securable();
		sec.setDisplayName("Table - Permissions");
		sec.setGuid((new Permission()).getSqlSecurableGuid());
		lstSecurables.add(sec);

		sec = new Securable();
		sec.setDisplayName("Table - Securables");
		sec.setGuid((new Securable()).getSqlSecurableGuid());
		lstSecurables.add(sec);

		sec = new Securable();
		sec.setDisplayName("Table - Settings");
		sec.setGuid((new Setting()).getSqlSecurableGuid());
		lstSecurables.add(sec);

		sec = new Securable();
		sec.setDisplayName("Table - Users");
		sec.setGuid((new User()).getSqlSecurableGuid());
		lstSecurables.add(sec);

		sec = new Securable();
		sec.setDisplayName("Table - Reports");
		sec.setGuid((new Report()).getSqlSecurableGuid());
		lstSecurables.add(sec);

		sec = new Securable();
		sec.setDisplayName("Table - Report Blocks");
		sec.setGuid((new ReportBlock()).getSqlSecurableGuid());
		lstSecurables.add(sec);

		sec = new Securable();
		sec.setDisplayName("Table - Report Filters");
		sec.setGuid((new ReportFilter()).getSqlSecurableGuid());
		lstSecurables.add(sec);

		sec = new Securable();
		sec.setDisplayName("Control - Options");
		sec.setGuid((new Option()).getSqlSecurableGuid());
		lstSecurables.add(sec);

		return lstSecurables;
	}
	private List<User> createUsers() throws Exception {
		User user = new User();
		user.setGuid(User.ADMINISTRATOR_GUID);
		user.setDisplayName("Administrator");
		user.setEmailAddress("administrator@localhost");
		user.setIsAllowed(true);
		user.setPassword(getPassword());
		user.setSqlUser("administrator");
		user.setAddComputer(false);
		Calendar cal = Calendar.getInstance();
		//cal.add(Calendar.DAY_OF_YEAR, -60);
		user.setPasswordDate(new java.sql.Date(cal.getTime().getTime()));
		LinkedList<User> lstUsers = new LinkedList<>();
		lstUsers.add(user);
		return lstUsers;
	}
	private List<Membership> createMemberships() throws Exception {
		LinkedList<Membership> lstMembers = new LinkedList<>();

		Membership mbr = new Membership();
		mbr.setGroupsGuid(Group.ADMINISTRATORS_GUID);
		mbr.setUsersGuid(User.ADMINISTRATOR_GUID);
		lstMembers.add(mbr);

		return lstMembers;
	}
	private List<Setting> createSettings() throws Exception {
		LinkedList<Setting> lstSettings = new LinkedList<>();

		Setting setting = new Setting();
		setting.initialize();
		setting.setKey(User.SETTING_PASSWORD_AGE);
		setting.setType(Setting.TYPE_NUMBER);
		setting.setValue("45");
		lstSettings.add(setting);

//		setting = new Setting();
//		setting.initialize();
//		setting.setKey(User.SETTING_TITLE_PREFIX);
//		setting.setType(Setting.TYPE_STRING);
//		setting.setValue("INC");
//		lstSettings.add(setting);

		setting = new Setting();
		setting.initialize();
		setting.setKey(User.CHECK_MAC_ADDRESS);
		setting.setType(Setting.TYPE_BOOLEAN);
		setting.setValue("F");
		lstSettings.add(setting);

		setting = new Setting();
		setting.initialize();
		setting.setKey(User.SETTING_PASSWORD_COMPLEXITY);
		setting.setType(Setting.TYPE_NUMBER);
		setting.setValue("3");
		lstSettings.add(setting);

		setting = new Setting();
		setting.initialize();
		setting.setKey(User.SETTING_PASSWORD_LENGTH);
		setting.setType(Setting.TYPE_NUMBER);
		setting.setValue("6");
		lstSettings.add(setting);

		setting = new Setting();
		setting.initialize();
		setting.setKey(Computer.SETTING_ENFORCE_COMPUTERS);
		setting.setType(Setting.TYPE_BOOLEAN);
		setting.setValue("1");
		lstSettings.add(setting);

		setting = new Setting();
		setting.initialize();
		setting.setKey(User.SETTING_VERSION_NUMBER);
		setting.setValue("2016.1.5.dev");
		setting.setType(Setting.TYPE_STRING);
		lstSettings.add(setting);

		setting = new Setting();
		setting.initialize();
		setting.setKey("History Length");
		setting.setValue("4");
		setting.setType(Setting.TYPE_NUMBER);
		lstSettings.add(setting);

		return lstSettings;
	}
	private List<Permission> createPermissions() throws Exception {
		List<Permission> lstPerms = new LinkedList<>();

		Permission perm;

		perm = new Permission();
		perm.setGroupsGuid(Group.EVERYONE_GUID);
		perm.setSecurablesGuid((new Computer()).getSqlSecurableGuid());
		perm.setCanCreate(false);
		perm.setCanRead(true);
		perm.setCanUpdate(false);
		perm.setCanDelete(false);
		lstPerms.add(perm);

		perm = new Permission();
		perm.setGroupsGuid(Group.EVERYONE_GUID);
		perm.setSecurablesGuid((new Group()).getSqlSecurableGuid());
		perm.setCanCreate(false);
		perm.setCanRead(true);
		perm.setCanUpdate(false);
		perm.setCanDelete(false);
		lstPerms.add(perm);

		perm = new Permission();
		perm.setGroupsGuid(Group.EVERYONE_GUID);
		perm.setSecurablesGuid((new Log()).getSqlSecurableGuid());
		perm.setCanCreate(true);
		perm.setCanRead(false);
		perm.setCanUpdate(false);
		perm.setCanDelete(false);
		lstPerms.add(perm);

		perm = new Permission();
		perm.setGroupsGuid(Group.EVERYONE_GUID);
		perm.setSecurablesGuid((new Membership()).getSqlSecurableGuid());
		perm.setCanCreate(false);
		perm.setCanRead(true);
		perm.setCanUpdate(false);
		perm.setCanDelete(false);
		lstPerms.add(perm);

		perm = new Permission();
		perm.setGroupsGuid(Group.EVERYONE_GUID);
		perm.setSecurablesGuid((new Permission()).getSqlSecurableGuid());
		perm.setCanCreate(false);
		perm.setCanRead(true);
		perm.setCanUpdate(false);
		perm.setCanDelete(false);
		lstPerms.add(perm);

		perm = new Permission();
		perm.setGroupsGuid(Group.EVERYONE_GUID);
		perm.setSecurablesGuid((new Securable()).getSqlSecurableGuid());
		perm.setCanCreate(false);
		perm.setCanRead(true);
		perm.setCanUpdate(false);
		perm.setCanDelete(false);
		lstPerms.add(perm);

		perm = new Permission();
		perm.setGroupsGuid(Group.EVERYONE_GUID);
		perm.setSecurablesGuid((new Setting()).getSqlSecurableGuid());
		perm.setCanCreate(false);
		perm.setCanRead(true);
		perm.setCanUpdate(false);
		perm.setCanDelete(false);
		lstPerms.add(perm);

		perm = new Permission();
		perm.setGroupsGuid(Group.EVERYONE_GUID);
		perm.setSecurablesGuid((new User()).getSqlSecurableGuid());
		perm.setCanCreate(false);
		perm.setCanRead(true);
		perm.setCanUpdate(false);
		perm.setCanDelete(false);
		lstPerms.add(perm);

		perm = new Permission();
		perm.setGroupsGuid(Group.EVERYONE_GUID);
		perm.setSecurablesGuid((new Option()).getSqlSecurableGuid());
		perm.setCanCreate(false);
		perm.setCanRead(true);
		perm.setCanUpdate(false);
		perm.setCanDelete(false);
		lstPerms.add(perm);

		return lstPerms;
	}
	private List<Computer> createComputers() throws Exception {
		String sMac = User.getMacAddress();

		Computer comp = new Computer();
		comp.setDescription("First Installed");
		comp.setGuid(User.newGuid());
		comp.setIsAllowed(true);
		comp.setMacAddress(sMac);

		List<Computer> lstComputers = new LinkedList<>();
		lstComputers.add(comp);
		return lstComputers;
	}
	private void createReport(AdapterInterface adapter) throws Exception {
		Report rpt = new Report();
		rpt.initialize();
		rpt.setGuid("cef027b31c704b17b9f97bc9489626cb");
		rpt.setDisplayName("Group Member List");
		rpt.setHtmlTemplate(
				"<!-- STOCK REPORT -->"
				+ "<!-- BEGIN Groups -->\n"
				+ "<h2>{Display Name}</h2>\n"
				+ "<div class=\"columns\">\n"
				+ "<!-- BEGIN Users -->\n"
				+ "<p>\n"
				+ "<b>{Display Name}</b><br />\n"
				+ "{SQL User}<br />\n"
				+ "{Email Address}\n"
				+ "</p>\n"
				+ "<!-- END Users -->\n"
				+ "</div>\n"
				+ "<!-- END Groups -->"
		);

		ReportFilter filter = new ReportFilter();
		filter.setGuid(User.newGuid());
		filter.setReportsGuid(rpt.getGuid());
		filter.setPrompt("Group Contains");
		filter.setDataType(ReportFilter.DT_TEXT);

		ReportBlock blkGroup = new ReportBlock();
		blkGroup.setGuid(User.newGuid());
		blkGroup.setName("Groups");
		blkGroup.setReportsGuid(rpt.getGuid());
		blkGroup.setPriority(1);
		blkGroup.setSqlQuery(
				"SELECT * \n"
				+ "FROM \"Groups\" \n"
				+ "WHERE\n"
				+ "\"Groups\".\"Display Name\" LIKE CONCAT('%', {Group Contains}, '%') OR {Group Contains} IS NULL"
		);

		ReportBlock blkUser = new ReportBlock();
		blkUser.setGuid(User.newGuid());
		blkUser.setName("Users");
		blkUser.setParentBlockGuid(blkGroup.getGuid());
		blkUser.setPriority(1);
		blkUser.setSqlQuery(
				"SELECT * FROM \"Users\" \n"
				+ "WHERE \"Users\".\"GUID\" IN (\n"
				+ "\n"
				+ "SELECT \"Users GUID\" FROM \"Memberships\" \n"
				+ "WHERE \"Memberships\".\"Groups GUID\" = {GUID}\n"
				+ "\n"
				+ ")"
		);

		adapter.save(Report.TABLE_NAME, rpt);
		adapter.save(ReportFilter.TABLE_NAME, filter);
		adapter.save(ReportBlock.TABLE_NAME, blkGroup);
		adapter.save(ReportBlock.TABLE_NAME, blkUser);
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
