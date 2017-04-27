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

import java.io.File;
import net.internetworkconsulting.data.SessionInterface;
import java.net.NetworkInterface;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.xml.bind.annotation.XmlElement;
import net.internetworkconsulting.bootstrap.data.MembershipsRow;
import net.internetworkconsulting.bootstrap.data.SettingsRow;
import net.internetworkconsulting.bootstrap.data.UsersRow;
import net.internetworkconsulting.data.mysql.Adapter;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.Helper;
import net.internetworkconsulting.data.mysql.Statement;

public class User extends UsersRow implements SessionInterface {
	public static String ADMINISTRATOR_GUID = "86b41969e95143c090fd93a4819c58a2";
	public static String SETTING_PASSWORD_AGE = "Password Age (Days)";
	public static String SETTING_PASSWORD_COMPLEXITY = "Password Complexity (1-4)";
	public static String SETTING_PASSWORD_LENGTH = "Password Length (1-4)";
	public static String SETTING_VERSION_NUMBER = "Version Number";
	public static String CHECK_MAC_ADDRESS = "Check MAC Address";
	//public static String SETTING_TITLE_PREFIX = "HTML Title Prefix";

	public static String newGuid() { return UUID.randomUUID().toString().replace("-", ""); }

	public User() { super(); }
	public void initialize() throws Exception { 
		this.setGuid(User.newGuid()); 
		this.setIsAllowed(true);
		this.setPasswordDate(new java.sql.Date(java.time.Instant.EPOCH.toEpochMilli()));
	}

	private static List<Option> lstOptions;
	public static List<Option> loadOptions(AdapterInterface adapter, boolean force) throws Exception {
		if (lstOptions != null && !force)
			return lstOptions;

		Statement stmt = new Statement(adapter.getSession().readJar(User.class, "User.loadOptions.sql"));
		List<Option> lst = adapter.load(Option.class, stmt);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		lstOptions = lst;
		return lst;
	}

	public static String DATABASE = "Database";
	private String sDatabase;
	@XmlElement
	public void setDatabase(String value) { sDatabase = value; }
	public String getDatabase() { return sDatabase; }

	public static String SQL_SERVER = "SQL Server";
	private String sSqlServer;
	@XmlElement
	public void setSqlServer(String value) { sSqlServer = value; }
	public String getSqlServer() { return sSqlServer; }

	public static String PASSWORD = "Password";
	private String sPassword;
	public String getPassword() { return sPassword; }
	@XmlElement
	public void setPassword(java.lang.String value) { sPassword = value; }

	public static String USER_SALT = "User Salt";
	private String sUserSalt = "_ ";
	@XmlElement
	public void setUserSalt(java.lang.String value) { sUserSalt = value; }
	public String getUserSalt() { return sUserSalt.replace("_", getDatabase() + "_"); }

	public static String PASSWORD_SALT = "Password Salt";
	private String sPasswordSalt = "4F*k| !hK]5";
	@XmlElement
	public void setPasswordSalt(java.lang.String value) { sPasswordSalt = value; }
	public String getPasswordSalt() { return sPasswordSalt; }
	
	public String desalinate(String value) {
		if(value == null)
			value = "";
		
		String ret = value;
		for(String salt : getPasswordSalt().split("\\s"))
			ret = ret.replace(salt, "");
		for(String salt : getUserSalt().split("\\s"))
			ret = ret.replace(salt, "");	
		return ret;
	}

	private AdapterInterface myAdapter;
	public AdapterInterface getAdapter() { return adapter; }
	private AdapterInterface adapter = null;
	private AdapterInterface loggingAdapter = null;
	private boolean isValidated = false;
	public AdapterInterface login() throws Exception {
		return login(true);
	}
	public AdapterInterface login(boolean validate) throws Exception {
		if (adapter != null && adapter.getConnection() != null && adapter.getSession() != null)
			return adapter;

		if (sPassword == null)
			throw new Exception("You must provide a password to login...  Either your session expired, or you are not logged in.  Please login and retry.");
		if (getUserSalt().indexOf(" ") != getUserSalt().lastIndexOf(" ") || !getUserSalt().contains(" "))
			throw new Exception("The user salt must contain exactly one space!");
		if (getPasswordSalt().indexOf(" ") != getPasswordSalt().lastIndexOf(" ") || !getUserSalt().contains(" "))
			throw new Exception("The password salt must have exactly one space!");

		String sSaltedUser = getUserSalt().replace(" ", getSqlUser());
		String sSaltedPassword = getPasswordSalt().replace(" ", sPassword);

		AdapterInterface tempAdapter = new Adapter(getSqlServer(), getDatabase(), sSaltedUser, sSaltedPassword, true);
		AdapterInterface tempLoggingAdapter = new Adapter(getSqlServer(), getDatabase(), sSaltedUser, sSaltedPassword, true);

		if (validate) {
			// load new user to become session
			User row = User.loadBySqlUser(tempAdapter, User.class, getSqlUser());
			this.setOriginals(row.getOriginals());
			this.setChanges(row.getChanges());

			// load settings
			List<Setting> lstSettings = loadSettings(tempAdapter, Setting.class, false);
			hmSettings = new HashMap();
			for(Setting setting : lstSettings) 
				hmSettings.put(setting.getKey(), setting.getValue());
			
			// check program vs database version
			checkVersion();
			
			// determine computer and check if allowed
			String chkMac = hmSettings.get(User.CHECK_MAC_ADDRESS);
			boolean isChkMac = (boolean) Statement.parseStringToValue(boolean.class, chkMac);			
			myComputer = isComputerAllowed(tempAdapter);
			if (myComputer == null && isChkMac) {
				if (!this.getAddComputer())
					throw new Exception("The computer is not authorized!");

				Computer rComputer = new Computer();
				rComputer.setDescription(this.getDisplayName() + "'s Computer");
				rComputer.setGuid(User.newGuid());
				rComputer.setIsAllowed(true);
				rComputer.setMacAddress(this.getMacAddress());
				List<Computer> lstComputers = new LinkedList<>();
				lstComputers.add(rComputer);
				tempAdapter.save(Computer.TABLE_NAME, lstComputers);
				myComputer = isComputerAllowed(tempAdapter);
			}

			// load securables
			hmSecurables = new HashMap<>();
			List<Securable> lstSecurables =  Securable.loadAll(tempAdapter, Securable.class);
			for (Securable sec : lstSecurables) 
				hmSecurables.put(sec.getGuid(), sec);

			// load memberships
			List<Membership> tempLstMemberships = this.loadMemberships(tempAdapter, Membership.class, false);
			hmPermissionsBySecurable = new HashMap<>();
			for (Membership mbr : tempLstMemberships) {
				Group grp = mbr.loadGroup(tempAdapter, Group.class, false);
				if (grp.getGuid().equals(Group.ADMINISTRATORS_GUID))
					bIsAdministrator = true;

				List<Permission> lstPerms = grp.loadPermissions(tempAdapter, Permission.class, false);
				for (Permission perm : lstPerms) {
					if(!hmPermissionsBySecurable.containsKey(perm.getSecurablesGuid())) {
						Permission newPerm = new Permission();
						newPerm.setSecurablesGuid(perm.getSecurablesGuid());
						hmPermissionsBySecurable.put(perm.getSecurablesGuid(), newPerm);
					}

					Permission myPerm = hmPermissionsBySecurable.get(perm.getSecurablesGuid());					
					myPerm.setCanCreate((myPerm.getCanCreate() != null && myPerm.getCanCreate()) || perm.getCanCreate());
					myPerm.setCanRead((myPerm.getCanRead() != null && myPerm.getCanRead()) || perm.getCanRead());
					myPerm.setCanUpdate((myPerm.getCanUpdate() != null && myPerm.getCanUpdate()) || perm.getCanUpdate());
					myPerm.setCanDelete((myPerm.getCanDelete() != null && myPerm.getCanDelete()) || perm.getCanDelete());
				}
			}

			// set validated and active session
			isValidated = true;
			tempAdapter.setSession(this);

			loggingAdapter = tempLoggingAdapter;
			logEvent("Logged in!", "28ea22a4879c447f96a29c58ddc5ff94");
		}

		adapter = tempAdapter;
		return adapter;
	}

	private List<Membership> lstMemberships = null;
	public <T extends MembershipsRow> List<T> loadMemberships(AdapterInterface adapter, Class biz, boolean force) throws Exception {
		if (lstMemberships == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Memberships\" WHERE \"Users GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstMemberships = adapter.load(Membership.class, stmt);

			Membership mbr = new Membership();
			mbr.setUsersGuid(getGuid());
			mbr.setGroupsGuid(Group.EVERYONE_GUID);

			lstMemberships.add(mbr);
		}
		return (List<T>) lstMemberships;
	}

	public void resetSqlPassword(AdapterInterface adapter, String password, String confirm) throws Exception {
		validatePassword(adapter, password, confirm);

		String sDbUser = getUserSalt().replace(" ", getSqlUser());
		String sDbPassword = getPasswordSalt().replace(" ", password);
		String strDatabase = adapter.getConnection().getCatalog();
		
		String sql;
		Statement stmt;

		try {
			sql = "DROP USER {USER}@'%';";
			stmt = new Statement(sql);
			stmt.getParameters().put("{USER}", sDbUser);
			adapter.execute(stmt, false);
		} catch (Exception ex) { }

		sql = "CREATE USER {USER}@'%' IDENTIFIED BY {PASSWORD} REQUIRE SSL;";
		stmt = new Statement(sql);
		stmt.getParameters().put("{USER}", sDbUser);
		stmt.getParameters().put("{PASSWORD}", sDbPassword);
		adapter.execute(stmt, false);

		sql = "GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON \"DATABASE\".* TO {USER}@'%';";
		sql = sql.replace("DATABASE", strDatabase);
		stmt = new Statement(sql);
		stmt.getParameters().put("{USER}", sDbUser);
		adapter.execute(stmt, false);
		
		this.setPasswordDate(new java.sql.Date(java.time.Instant.EPOCH.toEpochMilli()));
		adapter.save(User.TABLE_NAME, this);		
	}
	public void changePassword(AdapterInterface adapter, String password, String confirm) throws Exception {
		validatePassword(adapter, password, confirm);

		String sql = " ALTER USER {User}@'%' IDENTIFIED BY {Password}; \n";
		//sql += " ALTER USER {User}@'%' ACCOUNT UNLOCK; \n";
		//sql += " ALTER USER {User}@'localhost' IDENTIFIED BY {Password}; \n";
		//sql += " ALTER USER {User}@'localhost' ACCOUNT UNLOCK; \n";

		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{User}", getUserSalt().replace(" ", getSqlUser()));
		stmt.getParameters().put("{Password}", getPasswordSalt().replace(" ", password));
		adapter.execute(stmt, false);

		setPassword(password);
	}
	private void validatePassword(AdapterInterface adapter, String password, String confirm) throws Exception {
		if (password == null || confirm == null)
			throw new Exception("Password have not been set!");
		if (password.contains("'"))
			throw new Exception("The user name must not contain an apostrophe (')!");
		if (password.contains("'"))
			throw new Exception("The password must not contain an apostrophe (')!");
		if (!password.equals(confirm))
			throw new Exception("The password and confirmation do not match!");

		int iMinLength = Integer.parseInt(adapter.getSession().getSetting(User.SETTING_PASSWORD_LENGTH));
		if (password.length() < iMinLength)
			throw new Exception("The password must be at least " + adapter.getSession().getSetting(User.SETTING_PASSWORD_LENGTH) + " characters in length!");

		int iMinComplpexity = Integer.parseInt(adapter.getSession().getSetting(User.SETTING_PASSWORD_COMPLEXITY));
		if (getComplexity(password) < iMinComplpexity)
			throw new Exception("The password must contain " + adapter.getSession().getSetting(User.SETTING_PASSWORD_COMPLEXITY) + " of the following catagories: upper case, lower case, numbers, and symbols!");
	}
	private int getComplexity(String value) {
		boolean bUpper = false;
		boolean bLower = false;
		boolean bNumber = false;
		boolean bOther = false;

		for (int cnt = 0; cnt < value.length(); cnt++) {
			if (Character.isUpperCase(value.toCharArray()[cnt]))
				bUpper = true;
			else if (Character.isLowerCase(value.toCharArray()[cnt]))
				bLower = true;
			else if (Character.isDigit(value.toCharArray()[cnt]))
				bNumber = true;
			else
				bOther = true;
		}

		int cnt = 0;
		if (bUpper)
			cnt++;
		if (bLower)
			cnt++;
		if (bNumber)
			cnt++;
		if (bOther)
			cnt++;

		return cnt;
	}
	public boolean isPasswordExpired(AdapterInterface adapter) throws Exception {
		if (getPasswordDate() == null)
			return true;

		int iMaxDays = Integer.parseInt(adapter.getSession().getSetting(User.SETTING_PASSWORD_AGE));
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -iMaxDays);
		return getPasswordDate().before(cal.getTime());
	}

	private Computer myComputer;
	public Computer getComputer() { return myComputer; }
	public static String getMacAddress() throws Exception {
		Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
		while (en.hasMoreElements()) {
			NetworkInterface ni = en.nextElement();
			byte[] arrMac = ni.getHardwareAddress();
			if (arrMac != null)
				return Helper.ByteArrayToHex(arrMac);
		}

		throw new Exception("Could not locate a MAC address!");
	}
	public Computer isComputerAllowed(AdapterInterface adapter) throws Exception {
		Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
		while (en.hasMoreElements()) {
			NetworkInterface ni = en.nextElement();
			byte[] arrMac = ni.getHardwareAddress();
			if (arrMac == null)
				continue;
			
			String sMac = Helper.ByteArrayToHex(arrMac);
			Computer comp = null;
			try { comp = Computer.loadByMacAddress(adapter, Computer.class, sMac); }
			catch(Exception ex) { return null; }
			
			if (comp.getIsAllowed())
				return comp;
		}
		return null;
	}

	private boolean bIsAdministrator = false;
	private HashMap<String, Securable> hmSecurables;
	private HashMap<String, Permission> hmPermissionsBySecurable;
	public void canCreate(String securable_guid) throws Exception {
		if (getGuid().equals(User.ADMINISTRATOR_GUID) || bIsAdministrator)
			return;
		if (!hmPermissionsBySecurable.containsKey(securable_guid))
			throw new Exception("Unknown securable GUID: " + securable_guid + "!");
		if (!hmPermissionsBySecurable.get(securable_guid).getCanCreate())
			throw new Exception("You do not have create permissions to " + hmSecurables.get(securable_guid).getDisplayName() + "!");
	}
	public void canRead(String securable_guid) throws Exception {
		if (getGuid().equals(User.ADMINISTRATOR_GUID) || bIsAdministrator)
			return;
		if (!hmPermissionsBySecurable.containsKey(securable_guid))
			throw new Exception("Unknown securable GUID: " + securable_guid + "!");
		if (!hmPermissionsBySecurable.get(securable_guid).getCanRead())
			throw new Exception("You do not have read permissions to " + hmSecurables.get(securable_guid).getDisplayName() + "!");
	}
	public void canUpdate(String securable_guid) throws Exception {
		if (getGuid().equals(User.ADMINISTRATOR_GUID) || bIsAdministrator)
			return;
		if (!hmPermissionsBySecurable.containsKey(securable_guid))
			throw new Exception("Unknown securable GUID: " + securable_guid + "!");
		if (!hmPermissionsBySecurable.get(securable_guid).getCanUpdate())
			throw new Exception("You do not have update permissions to " + hmSecurables.get(securable_guid).getDisplayName() + "!");
	}
	public void canDelete(String securable_guid) throws Exception {
		if (getGuid().equals(User.ADMINISTRATOR_GUID) || bIsAdministrator)
			return;
		if (!hmPermissionsBySecurable.containsKey(securable_guid))
			throw new Exception("Unknown securable GUID: " + securable_guid + "!");
		if (!hmPermissionsBySecurable.get(securable_guid).getCanDelete())
			throw new Exception("You do not have read permissions to " + hmSecurables.get(securable_guid).getDisplayName() + "!");
	}

	public void logEvent(String message, String code_guid) {
		try {
			Log log = new Log();
			log.setGuid(User.newGuid());
			log.setCodeGuid(code_guid);
			log.setComputersGuid(this.getComputer().getGuid());
			log.setDetails(desalinate(message));
			log.setLog("Event");
			log.setOccured(new Timestamp((new Date()).getTime()));
			log.setUsersGuid(getGuid());
			loggingAdapter.save(Log.TABLE_NAME, log);
		} catch (Exception ex) { }
	}
	public void logExcpetion(Exception ex, String code_guid) {
		try {
			Log log = new Log();
			log.setGuid(User.newGuid());
			log.setCodeGuid(code_guid);
			log.setComputersGuid(this.getComputer().getGuid());

			String out = ex.getMessage();
			out += "\n\n";
			out += ex.toString();
			out += "\n\n";
			for (StackTraceElement st : ex.getStackTrace())
				out += st.toString() + "\n";

			log.setDetails(desalinate(out));
			log.setLog("Exception");
			log.setOccured(new Timestamp((new Date()).getTime()));
			log.setUsersGuid(getGuid());
			loggingAdapter.save(Log.TABLE_NAME, log);
		} catch (Exception exx) { }
	}
	public void logSql(String sql, String code_guid) {
		try {
			Log log = new Log();
			log.setGuid(User.newGuid());
			log.setCodeGuid(code_guid);
			log.setComputersGuid(this.getComputer().getGuid());
			log.setDetails(desalinate(sql));
			log.setLog("SQL");
			log.setOccured(new Timestamp((new Date()).getTime()));
			log.setUsersGuid(getGuid());
			loggingAdapter.save(Log.TABLE_NAME, log);
		} catch (Exception ex) { }
	}

	private String sProgramPath = null;
	public String getProgramPath() { return sProgramPath; }
	public void setProgramPath(String value) { sProgramPath = value; }
	
	private List<Setting> lstSettings = null;
	public <T extends SettingsRow> List<T> loadSettings(AdapterInterface adapter, Class biz, boolean force) throws Exception {
		if (lstSettings == null || force) {
			String sql = "SELECT * FROM \"%s\" WHERE \"%s\"={PRIMARYKEY} OR \"%s\" IS NULL ORDER BY \"%s\"";
			sql = String.format(sql, Setting.TABLE_NAME, Setting.USERS_GUID, Setting.USERS_GUID, Setting.USERS_GUID);
			Statement stmt = new Statement(sql);
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstSettings = adapter.load(biz, stmt);
		}
		return (List<T>) lstSettings;
	}
	private HashMap<String, String> hmSettings = new HashMap<>();
	public String getSetting(String key) { 
		if(hmSettings.containsKey(key))
			return hmSettings.get(key);
		else
			return null;
	}
	public void setSetting(String key, String value) { hmSettings.put(key, value); }
	
	public static String VersionNumber = "2016.1.5.dev";
	public String getVersion() { return User.VersionNumber; }
	public void checkVersion() throws Exception {
		if(!hmSettings.containsKey(SETTING_VERSION_NUMBER))
			throw new Exception("Could not locate a version number in the applications settings!");
		if(!hmSettings.get(SETTING_VERSION_NUMBER).equals(getVersion()))
			throw new Exception("The program version number does not match the database version number.");
	}
	
	public String readJar(Class jar, String filename) throws Exception {
		return Helper.InputStreamToString(jar.getResourceAsStream(filename));
	}
	public static String readJarFile(Class jar, String filename) throws Exception {
		return Helper.InputStreamToString(jar.getResourceAsStream(filename));
	}
				
//	public static <T extends User> T loadBySqlUser(AdapterInterface adapter, Class model, java.lang.String value) throws Exception {
//		String sql = "SELECT * FROM \"" + User.TABLE_NAME +"\" WHERE \"" + User.SQL_USER + "\"={VALUE}";
//		Statement stmt = new Statement(sql);
//		stmt.getParameters().put("{VALUE}", value);
//
//		List<T> lst = adapter.load(model, stmt);
//		if(lst.size() != 1)
//			throw new Exception("Could not locate unique Userrow by 'SQL User': " + Statement.convertObjectToString(value, null));
//
//		return lst.get(0);		
//	}
}
